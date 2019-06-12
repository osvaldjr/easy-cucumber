package io.github.osvaldjr.confs;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.osvaldjr.domains.exceptions.FeignException;

@Configuration
@EnableFeignClients(basePackages = {"io.github.osvaldjr.gateways.feign"})
public class FeignConfig {

  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignDecoder();
  }

  @Bean
  public Contract feignContract() {
    return new Contract.Default();
  }

  public class FeignDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
      return new FeignException(response);
    }
  }
}
