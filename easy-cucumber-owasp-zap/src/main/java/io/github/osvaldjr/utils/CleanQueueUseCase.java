package io.github.osvaldjr.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.configs.ActiveMQFeature;
import io.github.osvaldjr.gateways.activemq.ActiveMQGateway;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnBean(ActiveMQFeature.class)
public class CleanQueueUseCase {

  private final ActiveMQGateway activeMQGateway;

  @Autowired
  public CleanQueueUseCase(ActiveMQGateway activeMQGateway) {
    this.activeMQGateway = activeMQGateway;
  }

  public void execute(String destinationQueue) {
    activeMQGateway.cleanQueue(destinationQueue);
  }
}
