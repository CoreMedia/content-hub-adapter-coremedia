package com.coremedia.labs.plugins.adapters.coremedia.server;

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
  public ContentHubAdapterFactory<?> coreMediaContentHubAdapterFactory() {
    return new CoreMediaContentHubAdapterFactory();
  }
}
