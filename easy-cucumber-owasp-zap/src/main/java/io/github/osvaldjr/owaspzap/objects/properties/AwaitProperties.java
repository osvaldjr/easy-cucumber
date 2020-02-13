package io.github.osvaldjr.owaspzap.objects.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties
public class AwaitProperties {

  private int timeout = 1;
  private int retry = 5;
}
