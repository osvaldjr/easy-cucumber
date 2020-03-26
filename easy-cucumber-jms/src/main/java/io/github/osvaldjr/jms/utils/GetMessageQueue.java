package io.github.osvaldjr.jms.utils;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.osvaldjr.jms.objects.exceptions.QueueException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
