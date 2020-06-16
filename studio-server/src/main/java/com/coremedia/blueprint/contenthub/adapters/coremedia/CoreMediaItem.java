package com.coremedia.blueprint.contenthub.adapters.coremedia;

import com.coremedia.cap.common.UrlBlob;
import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.Item;
import com.coremedia.contenthub.api.UrlBlobBuilder;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class CoreMediaItem extends CoreMediaContentHubObject implements Item {

  private static final String MAIN = "main";
  private static final String DATA_PROPERTY = "data";
  private static final String NAME = "name";
  private static final String ID = "id";
  private static final String TYPE = "type";
  private static final String LAST_MODIFIED = "lastModified";
  private static final String METADATA = "metadata";
  private static final String PREVIEW = "preview";
  private static final String CM_PICTURE = "CMPicture";

  CoreMediaItem(Content content, ContentHubObjectId id) {
    super(content, id);
  }

  @Nullable
  @Override
  public String getDescription() {
    return getContent().getPath();
  }

  @NonNull
  @Override
  public String getCoreMediaContentType() {
    return getContent().getType().getName();
  }

  @NonNull
  @Override
  public List<DetailsSection> getDetails() {
    if (getContent().getType().getName().equals(CM_PICTURE)) {
      return pictureDetails();
    }
    return contentDetails();
  }

  private List<DetailsSection> contentDetails() {
    List<DetailsElement<?>> elements = List.of(new DetailsElement<>(getContent().getName(), SHOW_TYPE_ICON));
    return List.of(new DetailsSection(MAIN, elements, false, false, false), getMetaDataSection());
  }

  private List<DetailsSection> pictureDetails() {
    ContentHubBlob picture = new UrlBlobBuilder(this, PREVIEW).withUrl(getUrlBlob().getUrl()).withEtag().build();
    List<DetailsElement<?>> elements = List.of(new DetailsElement<>(getContent().getName(), false, picture));
    return List.of(new DetailsSection(MAIN, elements, false, false, false), getMetaDataSection());
  }

  private UrlBlob getUrlBlob() {
    return (UrlBlob) getContent().get(DATA_PROPERTY);
  }

  @Nullable
  @Override
  public ContentHubBlob getBlob(String classifier) {
    return new UrlBlobBuilder(this, classifier).withUrl(getUrlBlob().getUrl()).withEtag().build();
  }

  @NonNull
  private DetailsSection getMetaDataSection() {
    return new DetailsSection(METADATA, List.of(
            new DetailsElement<>(NAME, getContent().getName()),
            new DetailsElement<>(ID, getContent().getId()),
            new DetailsElement<>(TYPE, getContent().getType().getName()),
            new DetailsElement<>(LAST_MODIFIED, getContent().getModificationDate())
    ).stream().filter(p -> Objects.nonNull(p.getValue())).collect(Collectors.toUnmodifiableList()));
  }
}
