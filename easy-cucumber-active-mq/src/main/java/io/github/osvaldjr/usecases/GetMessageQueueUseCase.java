package io.github.osvaldjr.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.confs.ActiveMQFeature;
import io.github.osvaldjr.gateways.activemq.ActiveMQGateway;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnBean(ActiveMQFeature.class)
public class GetMessageQueueUseCase {

  private final ActiveMQGateway activeMQGateway;

  @Autowired
  public GetMessageQueueUseCase(ActiveMQGateway activeMQGateway) {
    this.activeMQGateway = activeMQGateway;
  }

  public Object execute(String destinationQueue) {
    Object message = activeMQGateway.getMessageQueue(destinationQueue);
    log.info("Received message from queue: {}", message);
    return message;
  }
}
