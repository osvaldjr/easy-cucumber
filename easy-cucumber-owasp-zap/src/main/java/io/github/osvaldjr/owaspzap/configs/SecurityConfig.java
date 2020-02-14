package io.github.osvaldjr.owaspzap.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zaproxy.clientapi.core.ClientApi;

import io.github.osvaldjr.owaspzap.objects.properties.ApplicationProperties;
import io.github.osvaldjr.owaspzap.objects.properties.OwaspProperties;

@Configuration
public class SecurityConfig {

  private final io.github.osvaldjr.owaspzap.objects.properties.ApplicationProperties
      applicationProperties;

  @Autowired
  public SecurityConfig(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Bean
  public ClientApi zapProxyApi() {
    OwaspProperties owasp = applicationProperties.getDependencies().getOwasp();
    return new ClientApi(owasp.getServer(), owasp.getPort());
  }
}
