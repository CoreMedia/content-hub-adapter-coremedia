package com.coremedia.blueprint.contenthub.adapters.coremedia;

import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cms.common.plugins.beansforplugins.plugin.CommonBeansForPluginsConfiguration;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import com.coremedia.contenthub.api.column.ColumnProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonBeansForPluginsConfiguration.class})
public class CoreMediaConfiguration {
  @Bean
  public ColumnProvider coreMediaContentHubColumnModelProvider() {
    return new CoreMediaColumnProvider();
  }

  @Bean
  public ContentHubAdapterFactory coreMediaContentHubAdapterFactory(CapConnection capConnection) {
    ContentRepository contentRepository = capConnection.getContentRepository();
    return new CoreMediaContentHubAdapterFactory(contentRepository);
  }
}
