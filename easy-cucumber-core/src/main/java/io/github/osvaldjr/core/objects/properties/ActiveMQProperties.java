package io.github.osvaldjr.core.objects.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties
public class ActiveMQProperties {

  private boolean autoconfigure = false;
  private String brokerUrl = "tcp://localhost:61616";
  private String user = "admin";
  private String password = "admin";
  private QueueProperties queues = new QueueProperties();

  @Getter
  @Setter
  @Component
  @ConfigurationProperties
  public class QueueProperties {

    private AwaitProperties await = new AwaitProperties();
    private List<String> names = new ArrayList<>();
  }

  @Getter
  @Setter
  @Component
  @ConfigurationProperties
  public class AwaitProperties {

    private int timeout = 1;
    private int retry = 5;
  }
}
