package br.community.component.test.confs;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.community.component.test.domains.exceptions.FeignException;
import feign.Contract;
import feign.Response;
import feign.codec.ErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = {"br.community.component.test.gateways.feign"})
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
