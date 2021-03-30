package com.coremedia.labs.plugins.adapters.coremedia.server;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.Folder;

class CoreMediaFolder extends CoreMediaContentHubObject implements Folder {

  CoreMediaFolder(Content folder, ContentHubObjectId id) {
    super(folder, id);
  }
}
