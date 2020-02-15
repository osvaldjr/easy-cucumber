package io.github.osvaldjr.jms.utils;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.core.utils.FileUtils;

@Component
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
