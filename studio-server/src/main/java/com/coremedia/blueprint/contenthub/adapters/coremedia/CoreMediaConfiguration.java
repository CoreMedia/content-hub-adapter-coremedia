package com.coremedia.blueprint.contenthub.adapters.coremedia;

import com.coremedia.cap.content.ContentRepository;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import com.coremedia.contenthub.api.column.ColumnProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreMediaConfiguration {
  @Bean
  public ColumnProvider coreMediaContentHubColumnModelProvider() {
    return new CoreMediaColumnProvider();
  }

  @Bean
  public ContentHubAdapterFactory coreMediaContentHubAdapterFactory(PluginBeans pluginBeans) {
    ContentRepository contentRepository = pluginBeans.getCapConnection().getContentRepository();
    return new CoreMediaContentHubAdapterFactory(contentRepository);
  }
}
