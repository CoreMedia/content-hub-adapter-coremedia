package com.coremedia;

import com.coremedia.cap.content.ContentRepository;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 *
 */
class CoreMediaContentHubAdapterFactory implements ContentHubAdapterFactory<CoreMediaContentHubConfiguration> {
  private ContentRepository contentRepository;

  CoreMediaContentHubAdapterFactory(ContentRepository contentRepository) {
    this.contentRepository = contentRepository;
  }

  @Override
  @NonNull
  public String getId() {
    return "coremdia";
  }

  @Override
  @NonNull
  public ContentHubAdapter createAdapter(@NonNull CoreMediaContentHubConfiguration settings,
                                         @NonNull String connectionId) {
    return new CoreMediaContentHubAdapter(settings, connectionId, contentRepository);
  }
}
