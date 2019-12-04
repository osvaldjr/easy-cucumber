package io.github.osvaldjr.confs;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.osvaldjr.domains.properties.ActiveMQProperties;
import io.github.osvaldjr.domains.properties.ApplicationProperties;

@Configuration
@ConditionalOnProperty(value = "dependencies.activemq.enabled", havingValue = "true")
public class ActiveMQFeature {

  private final ObjectMapper objectMapper;
  private final ApplicationProperties applicationProperties;

  @Autowired
  public ActiveMQFeature(ObjectMapper objectMapper, ApplicationProperties applicationProperties) {
    this.objectMapper = objectMapper;
    this.applicationProperties = applicationProperties;
  }

  @Bean(name = "easyCucumberJmsTemplate")
  public JmsTemplate jmsTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setMessageConverter(messageConverter());
    return template;
  }

  public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    ActiveMQProperties activemq = applicationProperties.getDependencies().getActivemq();
    connectionFactory.setBrokerURL(activemq.getBrokerUrl());
    if (StringUtils.isNotEmpty(activemq.getUser())) {
      connectionFactory.setUserName(activemq.getUser());
    }
    if (StringUtils.isNotEmpty(activemq.getPassword())) {
      connectionFactory.setPassword(activemq.getPassword());
    }
    return connectionFactory;
  }

  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(objectMapper);
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }
}
