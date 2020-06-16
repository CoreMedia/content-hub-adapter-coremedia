package com.coremedia.blueprint.contenthub.adapters.coremedia;


import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
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

  public Content getContent() {
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
