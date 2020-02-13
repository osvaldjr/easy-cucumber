package io.github.osvaldjr.activemq.utils;

import io.github.osvaldjr.activemq.configs.ActiveMQConfig;
import io.github.osvaldjr.core.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
@ConditionalOnBean(ActiveMQConfig.class)
public class PutMessageQueue {

  private final FileUtils file;

  @Qualifier("easyCucumberJmsTemplate")
  private final JmsTemplate jmsTemplate;

  @Autowired
  public PutMessageQueue(FileUtils file, JmsTemplate jmsTemplate) {
    this.file = file;
    this.jmsTemplate = jmsTemplate;
  }

  public void execute(String scenario, String destinationQueue, String file)
      throws FileNotFoundException {
    Object message = this.file.getObjectFromFile(scenario, "messages/" + file, Object.class);
    jmsTemplate.convertAndSend(destinationQueue, message);
  }
}
