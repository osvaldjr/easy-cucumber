package io.github.osvaldjr.objects.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "queues")
public class QueueProperties {

  private AwaitProperties await = new AwaitProperties();
  private List<String> names = new ArrayList<>();
}
