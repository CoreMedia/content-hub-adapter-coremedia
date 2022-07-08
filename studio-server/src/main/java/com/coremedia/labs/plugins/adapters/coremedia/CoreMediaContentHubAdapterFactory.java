package com.coremedia.labs.plugins.adapters.coremedia;

import com.coremedia.cap.content.ContentRepository;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;

@DefaultAnnotation(NonNull.class)
public class CoreMediaContentHubAdapterFactory implements ContentHubAdapterFactory<CoreMediaContentHubSettings> {

  private static final String ADAPTER_ID = "coremedia";

  private final ContentRepository contentRepository;

  public CoreMediaContentHubAdapterFactory(ContentRepository contentRepository) {
    this.contentRepository = contentRepository;
  }

  @Override
  public String getId() {
    return ADAPTER_ID;
  }

  @Override
  public ContentHubAdapter createAdapter(@NonNull CoreMediaContentHubSettings settings,
                                         @NonNull String connectionId) {
    return new CoreMediaContentHubAdapter(settings, connectionId, contentRepository);
  }

}
