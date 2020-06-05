package com.coremedia.blueprint.contenthub.adapters.coremedia;


import com.coremedia.cap.common.UrlBlob;
import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
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
  CoreMediaItem(Content content, ContentHubObjectId id) {
    super(content, id);
  }

  @Nullable
  @Override
  public String getDescription() {
    return content.getPath();
  }

  @NonNull
  @Override
  public ContentHubType getContentHubType() {
    return new ContentHubType(content.getType().getName());
  }

  @NonNull
  @Override
  public String getCoreMediaContentType() {
    return content.getType().getName();
  }

  @NonNull
  @Override
  public List<DetailsSection> getDetails() {
    if (content.getType().getName().equals("CMPicture")){
      return pictureDetails();
    }
    return contentDetails();
  }

  private List<DetailsSection> contentDetails() {
    List<DetailsElement<?>> elements = List.of(new DetailsElement<>(content.getName(), SHOW_TYPE_ICON));
    return List.of(new DetailsSection("main", elements, false, false , false), getMetaDataSection());
  }

  private List<DetailsSection> pictureDetails() {
    ContentHubBlob picture = new UrlBlobBuilder(this, "preview").withUrl(getUrlBlob().getUrl()).withEtag().build();
    List<DetailsElement<?>> elements = List.of(new DetailsElement<>(content.getName(), false, picture));
    return List.of(new DetailsSection("main", elements, false, false, false), getMetaDataSection());
  }

  private UrlBlob getUrlBlob() {
    return (UrlBlob) content.get("data");
  }

  @Nullable
  @Override
  public ContentHubBlob getBlob(String classifier) {
    return new UrlBlobBuilder(this, classifier).withUrl(getUrlBlob().getUrl()).withEtag().build();
  }

  @NonNull
  private DetailsSection getMetaDataSection() {
    return new DetailsSection("metadata", List.of(
            new DetailsElement<>("name", content.getName()),
            new DetailsElement<>("id", content.getId()),
            new DetailsElement<>("type", content.getType().getName()),
            new DetailsElement<>("lastModified", content.getModificationDate())
    ).stream().filter(p -> Objects.nonNull(p.getValue())).collect(Collectors.toUnmodifiableList()));
  }
}
