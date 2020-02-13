package io.github.osvaldjr.activemq.objects.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties
public class AwaitProperties {

  private int timeout = 1;
  private int retry = 5;
}
