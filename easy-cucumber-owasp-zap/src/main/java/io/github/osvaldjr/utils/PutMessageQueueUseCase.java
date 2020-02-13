package io.github.osvaldjr.utils;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.configs.ActiveMQFeature;
import io.github.osvaldjr.gateways.FileGateway;
import io.github.osvaldjr.gateways.activemq.ActiveMQGateway;

@Component
@ConditionalOnBean(ActiveMQFeature.class)
public class PutMessageQueueUseCase {

  private final ActiveMQGateway activeMQGateway;
  private final FileGateway fileGateway;

  @Autowired
  public PutMessageQueueUseCase(ActiveMQGateway activeMQGateway, FileGateway fileGateway) {
    this.activeMQGateway = activeMQGateway;
    this.fileGateway = fileGateway;
  }

  public void execute(String scenario, String destinationQueue, String file)
      throws FileNotFoundException {
    Object message = fileGateway.getObjectFromFile(scenario, "messages/" + file, Object.class);
    activeMQGateway.putMessageQueue(destinationQueue, message);
  }
}
