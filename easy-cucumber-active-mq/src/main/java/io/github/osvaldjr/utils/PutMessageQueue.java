package io.github.osvaldjr.utils;

import io.github.osvaldjr.configs.ActiveMQConfig;
import io.github.osvaldjr.gateways.FileGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
@ConditionalOnBean(ActiveMQConfig.class)
public class PutMessageQueue {

  private final FileGateway file;

  @Qualifier("easyCucumberJmsTemplate")
  private final JmsTemplate jmsTemplate;

  @Autowired
  public PutMessageQueue(FileGateway file, JmsTemplate jmsTemplate) {
    this.file = file;
    this.jmsTemplate = jmsTemplate;
  }

  public void execute(String scenario, String destinationQueue, String file)
      throws FileNotFoundException {
    Object message = this.file.getObjectFromFile(scenario, "messages/" + file, Object.class);
    jmsTemplate.convertAndSend(destinationQueue, message);
  }
}
