package com.coremedia.blueprint.contenthub.adapters.coremedia;


import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import edu.umd.cs.findbugs.annotations.NonNull;

abstract class CoreMediaContentHubObject implements ContentHubObject {

  private ContentHubObjectId hubId;
  private String name;

  private Content content;

  CoreMediaContentHubObject(Content content, ContentHubObjectId hubId) {
    this.hubId = hubId;
    this.content = content;
    this.name = content.getName();
  }

  @NonNull
  @Override
  public ContentHubType getContentHubType() {
    return new ContentHubType(getContent().getType().getName());
  }

  Content getContent() {
    return content;
  }

  @NonNull
  @Override
  public String getName() {
    return name;
  }

  @NonNull
  @Override
  public String getDisplayName() {
    return getName();
  }

  @NonNull
  @Override
  public ContentHubObjectId getId() {
    return hubId;
  }
}
