package com.bex.cpi_search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** Configuration class for application-specific beans. */
@Configuration
// @SuppressWarnings("DesignForExtension") // Use this if Checkstyle supports suppression
public class AppConfig {

  /**
   * Creates a {@link RestTemplate} bean.
   *
   * @return a new instance of {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
