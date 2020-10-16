package com.coremedia.blueprint.contenthub.adapters.coremedia;

import com.coremedia.cap.Cap;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;
import com.coremedia.cap.content.search.SearchResult;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubContext;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubTransformer;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.Folder;
import com.coremedia.contenthub.api.GetChildrenResult;
import com.coremedia.contenthub.api.Item;
import com.coremedia.contenthub.api.column.ColumnProvider;
import com.coremedia.contenthub.api.exception.ContentHubException;
import com.coremedia.contenthub.api.pagination.PaginationRequest;
import com.coremedia.contenthub.api.search.ContentHubSearchResult;
import com.coremedia.contenthub.api.search.ContentHubSearchService;
import com.coremedia.contenthub.api.search.Sort;
import com.coremedia.contenthub.api.search.SortDirection;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class CoreMediaContentHubAdapter implements ContentHubAdapter, ContentHubSearchService {
  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private final ContentHubObjectId rootId;
  private final CoreMediaFolder rootFolder;
  private final ContentRepository repository;
  private final Content rootContent;
  private List<String> ignoredTypes = new ArrayList<>();
  private final String connectionId;
  private final CoreMediaColumnProvider coreMediaColumnProvider;

  CoreMediaContentHubAdapter(@NonNull CoreMediaContentHubConfiguration settings, String connectionId) {
    this.connectionId = connectionId;
    this.coreMediaColumnProvider = new CoreMediaColumnProvider();
    try {
      String username = settings.getUsername();
      String pwd = settings.getPassword();
      String ior = settings.getIor();
      String path = settings.getPath();

      if (StringUtils.isEmpty(ior)) {
        throw new IllegalStateException("No 'ior' configuration found for connection '" + connectionId + "'!");
      }
      CapConnection con = Cap.connect(ior, username, pwd);
      repository = con.getContentRepository();
      rootContent = StringUtils.isEmpty(path) ? repository.getRoot() : repository.getChild(path);
      rootId = new ContentHubObjectId(connectionId, rootContent.getId());
      rootFolder = new CoreMediaFolder(rootContent, rootId);

      String ignoredTypesString = settings.getIgnoredTypes();
      if (!StringUtils.isEmpty(ignoredTypesString)) {
        ignoredTypes = new ArrayList<>(Arrays.asList(ignoredTypesString.split(",")));
      }

    } catch (RuntimeException e) {
      LOG.error("Failed to initialized adapter for CoreMedia repository: {}", e.getMessage());
      throw new ContentHubException("Failed to initialized content hub adapter for CoreMedia", e);
    }
  }

  @NonNull
  @Override
  public Folder getRootFolder(@NonNull ContentHubContext context) {
    return rootFolder;
  }

  @Nullable
  @Override
  public Item getItem(@NonNull ContentHubContext context, @NonNull ContentHubObjectId id) {
    String capId = id.getExternalId();
    Content content = repository.getContent(capId);
    return new CoreMediaItem(content, id);
  }

  @Nullable
  @Override
  public Folder getFolder(@NonNull ContentHubContext context, @NonNull ContentHubObjectId id) {
    String capId = id.getExternalId();
    Content content = repository.getContent(capId);
    return rootContent.equals(content) ? rootFolder : new CoreMediaFolder(content, id);
  }

  @Nullable
  @Override
  public Folder getParent(@NonNull ContentHubContext context, @NonNull ContentHubObject contentHubObject) {
    ContentHubObjectId id = contentHubObject.getId();
    if (rootId.equals(id)) {
      return null;
    }
    String capId = id.getExternalId();
    Content content = repository.getContent(capId);
    if (content.isRoot()) {
      return null;
    }
    Content parent = content.getParent();
    if (parent != null) {
      if (parent.equals(rootContent)) {
        return getRootFolder(context);
      }
      ContentHubObjectId parentId = new ContentHubObjectId(connectionId, parent.getId());
      return new CoreMediaFolder(parent, parentId);
    } else {
      return null;
    }
  }

  @Override
  @NonNull
  public ContentHubTransformer transformer() {
    throw new UnsupportedOperationException("To be implemented if needed");
  }

  @Override
  @NonNull
  public Optional<ContentHubSearchService> searchService() {
    return Optional.of(this);
  }

  @Override
  public Collection<ContentHubType> supportedTypes() {
    return repository.getContentTypes().stream()
            .filter(Predicate.not(this::isIgnored))
            .map(CoreMediaContentHubAdapter::contentTypeAsContentHubType)
            .collect(Collectors.toUnmodifiableList());
  }

  private static ContentHubType contentTypeAsContentHubType(ContentType contentType) {
    ContentType parent = contentType.getParent();
    return new ContentHubType(contentType.getName(), parent == null ? null : contentTypeAsContentHubType(parent));
  }

  @Nullable
  private ContentType contentHubTypeAsContentType(@Nullable ContentHubType type) {
    return type == null ? null : repository.getContentType(type.getName());
  }

  @Override
  public GetChildrenResult getChildren(ContentHubContext contentHubContext, Folder folder, @Nullable PaginationRequest paginationRequest) {
    CoreMediaContentHubObject item = (CoreMediaContentHubObject) folder;
    Set<Content> children = item.getContent().getChildren();
    String connectionId = item.getId().getConnectionId();
    List<ContentHubObject> hubList = children
            .stream()
            .filter(Objects::nonNull)
            .filter(Content::isReadable)
            .filter(c -> !ignoredTypes.contains(c.getType().getName()))
            .map(c -> (c.isDocument()) ?
                    new CoreMediaItem(c, new ContentHubObjectId(connectionId, c.getId())) :
                    new CoreMediaFolder(c, new ContentHubObjectId(connectionId, c.getId())))
            .collect(Collectors.toList());

    return new GetChildrenResult(hubList);
  }

  @Override
  public ContentHubSearchResult search(String query,
                                       @Nullable Folder belowFolder,
                                       @Nullable ContentHubType type,
                                       Collection<String> filterQueries,
                                       List<Sort> sortBy,
                                       int limit) {

    // this adapter does not support multi-level sorting: all but the first sort criteria are ignored
    String sortField = sortBy.isEmpty() ? null : sortBy.get(0).getField();
    boolean sortAscending = sortBy.isEmpty() || sortBy.get(0).getDirection() == SortDirection.ASCENDING;

    Content belowContent = belowFolder instanceof CoreMediaFolder ? ((CoreMediaFolder) belowFolder).getContent() : rootContent;
    if (!belowContent.isChildOf(rootContent)) {
      belowContent = rootContent;
    }

    ContentType contentType = contentHubTypeAsContentType(type);

    SearchResult searchResult = repository.getSearchService().search(
            query, sortField, sortAscending, belowContent, true, contentType, true, 0, limit);

    List<CoreMediaContentHubObject> hits = searchResult.getMatches().stream()
            .flatMap(content -> contentHubObject(content).stream())
            .collect(Collectors.toUnmodifiableList());

    // Constructing result without total count.
    // It cannot be determined from the search result because the UAPI result may include ignored types.
    return new ContentHubSearchResult(hits);
  }

  @Override
  public boolean supportsSearchBelowFolder() {
    return true;
  }

  //----------------------- Helper -------------------------------------------------------------------------------------

  private Optional<CoreMediaContentHubObject> contentHubObject(Content content) {
    if (content.equals(rootContent)) {
      return Optional.of(rootFolder);
    }
    if (!content.isReadable() || !content.isChildOf(rootContent) || isIgnored(content)) {
      return Optional.empty();
    }
    if (content.isFolder()) {
      return Optional.of(new CoreMediaFolder(content, new ContentHubObjectId(connectionId, content.getId())));
    }
    Content parent = content.getParent();
    if (parent == null) {
      return Optional.empty();
    }
    ContentHubObjectId itemId = new ContentHubObjectId(connectionId, content.getId());
    return Optional.of(new CoreMediaItem(content, itemId));
  }

  private boolean isIgnored(Content childDocument) {
    return isIgnored(childDocument.getType());
  }

  private boolean isIgnored(ContentType contentType) {
    for (String ignoredType : ignoredTypes) {
      if (contentType.isSubtypeOf(ignoredType)) {
        return true;
      }
    }
    return false;
  }

  @NonNull
  @Override
  public ColumnProvider columnProvider() {
    return coreMediaColumnProvider;
  }
}
