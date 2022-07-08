package com.coremedia.labs.plugins.adapters.coremedia.model;


import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreMediaContentHubObject implements ContentHubObject {

  private final ContentHubObjectId hubId;
  private final String name;

  private final Content content;

  public CoreMediaContentHubObject(Content content, ContentHubObjectId hubId) {
    this(content, hubId, null);
  }

  public CoreMediaContentHubObject(Content content, ContentHubObjectId hubId, String name) {
    this.hubId = hubId;
    this.content = content;
    this.name = StringUtils.isNotBlank(name) ? name : content.getName();
  }

  @NonNull
  @Override
  public ContentHubType getContentHubType() {
    return new ContentHubType(getContent().getType().getName());
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
