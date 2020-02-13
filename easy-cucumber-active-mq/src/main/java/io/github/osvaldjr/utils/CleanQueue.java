package io.github.osvaldjr.utils;

import io.github.osvaldjr.configs.ActiveMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.Enumeration;

@Component
@Slf4j
@ConditionalOnBean(ActiveMQConfig.class)
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
