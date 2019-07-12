package io.github.osvaldjr.gateways.activemq;

import java.util.Enumeration;

import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ActiveMQGateway {

  private final JmsTemplate jmsTemplate;
  private final ObjectMapper objectMapper;

  @Autowired
  public ActiveMQGateway(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
    this.jmsTemplate = jmsTemplate;
    this.objectMapper = objectMapper;
  }

  public void putMessageQueue(String destinationQueue, Object message) {
    jmsTemplate.convertAndSend(destinationQueue, message);
  }

  public void cleanQueue(String destinationQueue) {
    Queue queue = () -> destinationQueue;
    jmsTemplate.browse(
        destinationQueue,
        (session, queueBrowser) -> {
          Enumeration<?> enumeration = queueBrowser.getEnumeration();
          int total = 0;
          while (enumeration.hasMoreElements()) {
            TextMessage message = (TextMessage) enumeration.nextElement();
            MessageConsumer consumer =
                session.createConsumer(queue, "JMSMessageID='" + message.getJMSMessageID() + "'");
            if (consumer.receive(1000) != null) {
              log.info("Remove message from ActiveMQ queue {}: {}", queue, message);
            }
            total++;
          }
          return total;
        });
  }

  public Object getMessageQueue(String destinationQueue) {
    ActiveMQTextMessage message = ((ActiveMQTextMessage) jmsTemplate.receive(destinationQueue));
    try {
      return objectMapper.readValue(message.getText(), Object.class);
    } catch (Exception e) {
      throw new RuntimeException("Error to receive message from ActiveMQ");
    }
  }
}
