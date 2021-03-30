package com.coremedia.labs.plugins.adapters.coremedia.server;

import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

class CoreMediaContentHubAdapterFactory implements ContentHubAdapterFactory<CoreMediaContentHubConfiguration> {
  @Override
  @NonNull
  public String getId() {
    return "coremedia";
  }

  @Override
  @NonNull
  public ContentHubAdapter createAdapter(@NonNull CoreMediaContentHubConfiguration settings,
                                         @NonNull String connectionId) {
    return new CoreMediaContentHubAdapter(settings, connectionId);
  }
}
