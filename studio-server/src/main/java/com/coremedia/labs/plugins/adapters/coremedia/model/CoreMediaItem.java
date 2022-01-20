package com.coremedia.labs.plugins.adapters.coremedia.model;

import com.coremedia.cap.common.Blob;
import com.coremedia.cap.common.UrlBlob;
import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.*;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.coremedia.contenthub.api.ContentHubBlob.THUMBNAIL_BLOB_CLASSIFIER;

public class CoreMediaItem extends CoreMediaContentHubObject implements Item {

  private static final String MAIN = "main";
  private static final String DATA_PROPERTY = "data";
  private static final String NAME = "name";
  private static final String ID = "id";
  private static final String TYPE = "type";
  private static final String LAST_MODIFIED = "lastModified";
  private static final String METADATA = "metadata";
  private static final String PREVIEW = "preview";
  private static final String CM_PICTURE = "CMPicture";

  public CoreMediaItem(Content content, ContentHubObjectId id) {
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
    return List.of(
            // Name & icon
            new DetailsSection(MAIN, List.of(
                    new DetailsElement<>(getName(), SHOW_TYPE_ICON)),
                    false, false, false),
            // Metadata
            getMetaDataSection());
  }

  private List<DetailsSection> pictureDetails() {
    ContentHubBlob thumbnail = getBlob(PREVIEW);
    return List.of(
            // Name & thumbnail
            new DetailsSection(MAIN, List.of(
                    new DetailsElement<>(getName(), false, Objects.requireNonNullElse(thumbnail, SHOW_TYPE_ICON))
            ), false, false, false),
            // Metadata
            getMetaDataSection());
  }

  private UrlBlob getUrlBlob() {
    if (getContent().getProperties().containsKey(DATA_PROPERTY)) {
      return (UrlBlob) getContent().get(DATA_PROPERTY);
    }
    return null;
  }

  private Blob getDataBlob() {
    if (getContent().getProperties().containsKey(DATA_PROPERTY)) {
      return getContent().getBlob(DATA_PROPERTY);
    }
    return null;
  }

  @Nullable
  @Override
  public ContentHubBlob getBlob(String classifier) {
    ContentHubBlob blob = null;
    try {
      Blob dataBlob = getDataBlob();
      if (dataBlob != null) {
        blob = new ContentHubDefaultBlob(
                this,
                classifier,
                dataBlob.getContentType(),
                dataBlob.getSize(),
                dataBlob::getInputStream,
                dataBlob.getETag());
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot create blob for " + this, e);
    }
    return blob;
  }

  @Nullable
  @Override
  public ContentHubBlob getThumbnailBlob() {
    ContentHubBlob thumbnailBlob = null;
    if (getContent().getType().getName().equals(CM_PICTURE)) {
      thumbnailBlob = getBlob(THUMBNAIL_BLOB_CLASSIFIER);
    }
    return thumbnailBlob;
  }

  @NonNull
  private DetailsSection getMetaDataSection() {
    return new DetailsSection(METADATA, List.of(
            new DetailsElement<>(NAME, getName()),
            new DetailsElement<>(ID, getContent().getId()),
            new DetailsElement<>(TYPE, getContent().getType().getName()),
            new DetailsElement<>(LAST_MODIFIED, getContent().getModificationDate())
    ).stream().filter(p -> Objects.nonNull(p.getValue())).collect(Collectors.toUnmodifiableList()));
  }
}
