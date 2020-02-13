package io.github.osvaldjr.activemq.objects.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties
public class ActiveMQProperties {

  private String brokerUrl = "tcp://localhost:61616";
  private String user = "";
  private String password = "";
}
