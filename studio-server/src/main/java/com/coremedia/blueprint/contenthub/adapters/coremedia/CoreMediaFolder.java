package com.coremedia.blueprint.contenthub.adapters.coremedia;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.Folder;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

class CoreMediaFolder extends CoreMediaContentHubObject implements Folder {

  CoreMediaFolder(Content folder, ContentHubObjectId id) {
    super(folder, id);
  }
}
