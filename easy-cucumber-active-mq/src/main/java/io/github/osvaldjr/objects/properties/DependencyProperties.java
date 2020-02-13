package io.github.osvaldjr.objects.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties
public class DependencyProperties {

  private ActiveMQProperties activemq = new ActiveMQProperties();
}
