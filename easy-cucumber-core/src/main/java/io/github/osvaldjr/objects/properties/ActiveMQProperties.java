package io.github.osvaldjr.objects.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties
public class ActiveMQProperties {

  private String brokerUrl = "tcp://localhost:61616";
  private String user = "";
  private String password = "";
}
