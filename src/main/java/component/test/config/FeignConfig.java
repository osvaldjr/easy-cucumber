package component.test.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
@EnableFeignClients(basePackages = {"component.test.feign"})
public class FeignConfig {
  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.BASIC;
  }

  @Bean
  public Feign.Builder feignBuilder() {
    return Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder());
  }

  @Bean
  public Contract feignContract() {
    return new Contract.Default();
  }
}
