package io.github.osvaldjr.jms.configs;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.osvaldjr.core.objects.properties.ActiveMQProperties;
import io.github.osvaldjr.core.objects.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "easycucumber.dependencies.activemq.autoconfigure", havingValue = "true")
public class ActiveMQConfig {

  private final ObjectMapper objectMapper;
  private final ApplicationProperties applicationProperties;

  @Autowired
  public ActiveMQConfig(ObjectMapper objectMapper, ApplicationProperties applicationProperties) {
    this.objectMapper = objectMapper;
    this.applicationProperties = applicationProperties;
  }

  @Bean(name = "easyCucumberJmsTemplate")
  public JmsTemplate jmsTemplate() {
    ActiveMQProperties activemq = applicationProperties.getDependencies().getActivemq();
    log.info(
        "Easy Cucumber auto configure jms template for ActiveMQ, brokerUrl = {}, = user {}, = password {}",
        activemq.getBrokerUrl(),
        activemq.getUser(),
        activemq.getPassword());

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
