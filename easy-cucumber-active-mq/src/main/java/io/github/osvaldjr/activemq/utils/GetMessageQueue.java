package io.github.osvaldjr.activemq.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.osvaldjr.activemq.configs.ActiveMQConfig;
import io.github.osvaldjr.activemq.objects.exceptions.QueueException;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnBean(ActiveMQConfig.class)
public class GetMessageQueue {

  @Qualifier("easyCucumberJmsTemplate")
  private final JmsTemplate jmsTemplate;

  private final ObjectMapper objectMapper;

  @Autowired
  public GetMessageQueue(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
    this.jmsTemplate = jmsTemplate;
    this.objectMapper = objectMapper;
  }

  public Object execute(String destinationQueue) {
    Object value;
    ActiveMQTextMessage message = ((ActiveMQTextMessage) jmsTemplate.receive(destinationQueue));
    try {
      value = objectMapper.readValue(message.getText(), Object.class);
    } catch (Exception e) {
      throw new QueueException("Error to receive message from ActiveMQ");
    }
    log.info("Received message from queue: {}", value);
    return value;
  }
}
