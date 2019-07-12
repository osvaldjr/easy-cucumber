package io.github.osvaldjr.unit.gateways.activemq;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQQueueBrowser;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.MessageId;
import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.activemq.ActiveMQGateway;
import io.github.osvaldjr.unit.UnitTest;

class ActiveMQGatewayTest extends UnitTest {

  @Mock private JmsTemplate jmsTemplate;
  @Mock private ObjectMapper objectMapper;
  @InjectMocks private ActiveMQGateway activeMQGateway;
  @Captor private ArgumentCaptor<String> queueNameCaptor;
  @Captor private ArgumentCaptor<BrowserCallback<?>> actionCaptor;

  @Mock private ActiveMQSession session;
  @Mock private ActiveMQQueueBrowser queueBrowser;
  @Mock private ActiveMQMessageConsumer messageConsumer;
  @Captor private ArgumentCaptor<Queue> queueCaptor;
  @Captor private ArgumentCaptor<String> messageSelectorCaptor;

  @Test
  void shouldPutMessageQueue(@Random String destinationQueue, @Random Object message) {
    activeMQGateway.putMessageQueue(destinationQueue, message);

    verify(jmsTemplate, times(1)).convertAndSend(destinationQueue, message);
  }

  @Test
  void shouldGetMessageQueue(@Random String destinationQueue, @Random Object object)
      throws JMSException, IOException {
    ActiveMQTextMessage mockMessage = new ActiveMQTextMessage();
    mockMessage.setText("text");
    when(jmsTemplate.receive(destinationQueue)).thenReturn(mockMessage);
    when(objectMapper.readValue(mockMessage.getText(), Object.class)).thenReturn(object);

    Object message = activeMQGateway.getMessageQueue(destinationQueue);

    assertThat(message, equalTo(object));
    verify(jmsTemplate, times(1)).receive(destinationQueue);
    verify(objectMapper, times(1)).readValue(mockMessage.getText(), Object.class);
  }

  @Test
  void shouldOccurredErrorConverterInGetMessageQueue(@Random String destinationQueue)
      throws JMSException, IOException {
    ActiveMQTextMessage mockMessage = new ActiveMQTextMessage();
    mockMessage.setText("text");
    when(jmsTemplate.receive(destinationQueue)).thenReturn(mockMessage);
    when(objectMapper.readValue(mockMessage.getText(), Object.class))
        .thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> activeMQGateway.getMessageQueue(destinationQueue));

    verify(jmsTemplate, times(1)).receive(destinationQueue);
    verify(objectMapper, times(1)).readValue(mockMessage.getText(), Object.class);
  }

  @Test
  void shouldCleanQueue(@Random String destinationQueue) throws JMSException {
    when(jmsTemplate.browse(queueNameCaptor.capture(), actionCaptor.capture())).thenReturn(null);
    when(queueBrowser.getEnumeration()).thenReturn(getTextMessageEnumeration());
    when(session.createConsumer(queueCaptor.capture(), messageSelectorCaptor.capture()))
        .thenReturn(messageConsumer);
    when(messageConsumer.receive(1000)).thenReturn(new ActiveMQTextMessage());

    activeMQGateway.cleanQueue(destinationQueue);

    BrowserCallback<?> browserCallback = actionCaptor.getValue();
    assertThat(browserCallback, notNullValue());

    Integer total = ((Integer) browserCallback.doInJms(session, queueBrowser));
    assertThat(total, equalTo(2));
    assertThat(queueNameCaptor.getValue(), equalTo(destinationQueue));
    assertTrue(
        queueCaptor
            .getAllValues()
            .stream()
            .allMatch(
                value -> {
                  try {
                    return value.getQueueName().equals(destinationQueue);
                  } catch (Exception e) {
                  }
                  return false;
                }));
    assertThat(messageSelectorCaptor.getAllValues().get(0), equalTo("JMSMessageID='ID:1'"));
    assertThat(messageSelectorCaptor.getAllValues().get(1), equalTo("JMSMessageID='ID:2'"));
  }

  @Test
  void shouldCleanQueueWithoutMessages(@Random String destinationQueue) throws JMSException {
    when(jmsTemplate.browse(queueNameCaptor.capture(), actionCaptor.capture())).thenReturn(null);
    when(queueBrowser.getEnumeration())
        .thenReturn(new IteratorEnumeration(new ArrayList<>().iterator()));

    activeMQGateway.cleanQueue(destinationQueue);

    BrowserCallback<?> browserCallback = actionCaptor.getValue();
    assertThat(browserCallback, notNullValue());

    Integer total = ((Integer) browserCallback.doInJms(session, queueBrowser));
    assertThat(total, equalTo(0));
    assertThat(queueNameCaptor.getValue(), equalTo(destinationQueue));
    verify(session, never()).createConsumer(Mockito.any(), anyString());
    verify(messageConsumer, never()).receive(anyInt());
  }

  @Test
  void shouldCleanQueueWithReceiveNullable(@Random String destinationQueue) throws JMSException {
    when(jmsTemplate.browse(queueNameCaptor.capture(), actionCaptor.capture())).thenReturn(null);
    when(queueBrowser.getEnumeration()).thenReturn(getTextMessageEnumeration());
    when(session.createConsumer(queueCaptor.capture(), messageSelectorCaptor.capture()))
        .thenReturn(messageConsumer);
    when(messageConsumer.receive(1000)).thenReturn(null);

    activeMQGateway.cleanQueue(destinationQueue);

    BrowserCallback<?> browserCallback = actionCaptor.getValue();
    assertThat(browserCallback, notNullValue());

    Integer total = ((Integer) browserCallback.doInJms(session, queueBrowser));
    assertThat(total, equalTo(2));
    assertThat(queueNameCaptor.getValue(), equalTo(destinationQueue));
    assertTrue(
        queueCaptor
            .getAllValues()
            .stream()
            .allMatch(
                value -> {
                  try {
                    return value.getQueueName().equals(destinationQueue);
                  } catch (Exception e) {
                  }
                  return false;
                }));
    assertThat(messageSelectorCaptor.getAllValues().get(0), equalTo("JMSMessageID='ID:1'"));
    assertThat(messageSelectorCaptor.getAllValues().get(1), equalTo("JMSMessageID='ID:2'"));
  }

  private Enumeration<TextMessage> getTextMessageEnumeration() throws JMSException {
    List<TextMessage> setEnumeration = new ArrayList<>();

    ActiveMQTextMessage activeMQTextMessage1 = new ActiveMQTextMessage();
    activeMQTextMessage1.setText("text1");
    activeMQTextMessage1.setMessageId(new MessageId("ID:1"));
    setEnumeration.add(activeMQTextMessage1);

    ActiveMQTextMessage activeMQTextMessage2 = new ActiveMQTextMessage();
    activeMQTextMessage2.setText("text2");
    activeMQTextMessage2.setMessageId(new MessageId("ID:2"));
    setEnumeration.add(activeMQTextMessage2);

    return (Enumeration<TextMessage>) new IteratorEnumeration(setEnumeration.iterator());
  }
}
