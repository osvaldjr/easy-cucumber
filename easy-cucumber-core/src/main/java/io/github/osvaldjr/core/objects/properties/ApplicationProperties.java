package io.github.osvaldjr.core.objects.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "easycucumber")
public class ApplicationProperties {

  private TargetProperties target = new TargetProperties();
  private DependencyProperties dependencies = new DependencyProperties();

  @Getter
  @Setter
  @Component
  @ConfigurationProperties
  public class TargetProperties {

    private String url;
  }

  @Getter
  @Setter
  @Component
  @ConfigurationProperties
  public class DependencyProperties {

    private OwaspZapProperties owasp = new OwaspZapProperties();
    private ActiveMQProperties activemq = new ActiveMQProperties();
    private DatasourceProperties datasource = new DatasourceProperties();
    private Stubby4NodeProperties stubby = new Stubby4NodeProperties();
    private MockServerProperties mockserver = new MockServerProperties();
  }
}
