package com.coremedia.labs.plugins.adapters.coremedia.model;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.Folder;

public class CoreMediaFolder extends CoreMediaContentHubObject implements Folder {

  public CoreMediaFolder(Content folder, ContentHubObjectId id) {
    super(folder, id);
  }

  public CoreMediaFolder(Content folder, ContentHubObjectId id, String name) {
    super(folder, id, name);
  }


}
