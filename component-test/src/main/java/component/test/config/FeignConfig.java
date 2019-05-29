package component.test.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.Logger;

@Configuration
@EnableFeignClients(basePackages = {"component.test.gateways.feign"})
public class FeignConfig {
  @Bean
  public Contract feignContract() {
    return new Contract.Default();
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
