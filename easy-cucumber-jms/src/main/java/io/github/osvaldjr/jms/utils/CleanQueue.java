package io.github.osvaldjr.jms.utils;

import java.util.Enumeration;

import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CleanQueue {

  @Qualifier("easyCucumberJmsTemplate")
  private final JmsTemplate jmsTemplate;

  @Autowired
  public CleanQueue(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public void execute(String destinationQueue) {
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
}
