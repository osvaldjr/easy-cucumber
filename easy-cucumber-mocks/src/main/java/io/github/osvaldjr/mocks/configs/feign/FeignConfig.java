package io.github.osvaldjr.mocks.configs.feign;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import io.github.osvaldjr.core.configs.converter.MessageConverter;
import io.github.osvaldjr.core.configs.decoder.FeignDecoder;
import io.github.osvaldjr.core.configs.decoder.FeignErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableFeignClients(basePackages = {"io.github.osvaldjr.clients"})
public class FeignConfig {

  @Bean
  @ConditionalOnMissingBean(ErrorDecoder.class)
  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }

  @Bean
  @ConditionalOnMissingBean(Decoder.class)
  public Decoder decoder() {
    return new FeignDecoder();
  }

  @Bean
  @ConditionalOnMissingBean(Encoder.class)
  public Encoder encoder() {
    return new SpringEncoder(new MessageConverter());
  }
}
