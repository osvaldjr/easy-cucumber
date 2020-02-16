package io.github.osvaldjr.core.objects.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties
public class MockServerProperties {

  private String host = "localhost";
  private Integer port = 9003;
}
