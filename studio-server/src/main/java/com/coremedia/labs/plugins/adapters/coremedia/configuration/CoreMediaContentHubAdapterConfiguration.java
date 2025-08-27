package com.coremedia.labs.plugins.adapters.coremedia.configuration;

import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cms.uapi.config.CapRepositoriesConfiguration;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import com.coremedia.contenthub.api.column.ColumnProvider;
import com.coremedia.labs.plugins.adapters.coremedia.CoreMediaColumnProvider;
import com.coremedia.labs.plugins.adapters.coremedia.CoreMediaContentHubAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CapRepositoriesConfiguration.class})
public class CoreMediaContentHubAdapterConfiguration {

  @Bean
  public ColumnProvider coreMediaContentHubColumnModelProvider() {
    return new CoreMediaColumnProvider();
  }

  @Bean
  public ContentHubAdapterFactory<?> coreMediaContentHubAdapterFactory(ContentRepository contentRepository) {
    return new CoreMediaContentHubAdapterFactory(contentRepository);
  }
}
