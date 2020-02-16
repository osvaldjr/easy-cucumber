package io.github.osvaldjr.core.objects.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties
public class DatasourceProperties {

  private boolean autoconfigure = false;
  private String url = "jdbc:postgresql://localhost:5432/easycucumber";
  private String username = "admin";
  private String password = "admin";
}
