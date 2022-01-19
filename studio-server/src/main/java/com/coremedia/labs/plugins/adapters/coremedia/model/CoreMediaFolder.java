package com.coremedia.labs.plugins.adapters.coremedia.model;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.Folder;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

public class CoreMediaFolder extends CoreMediaContentHubObject implements Folder {

  public CoreMediaFolder(Content folder, ContentHubObjectId id) {
    super(folder, id);
  }

  public CoreMediaFolder(Content folder, ContentHubObjectId id, String name) {
    super(folder, id, name);
  }



}
