package io.github.osvaldjr.domains.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "queues")
public class QueueProperties {

  private AwaitProperties await = new AwaitProperties();
  private List<String> names = new ArrayList<>();
}
