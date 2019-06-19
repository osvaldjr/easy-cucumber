package io.github.osvaldjr.confs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.osvaldjr.domains.ClientResponse;
import io.github.osvaldjr.domains.ClientResponse.ClientResponseBuilder;
import io.github.osvaldjr.domains.exceptions.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
      ClientResponseBuilder exceptionClientResponse =
          ClientResponse.builder()
              .status(response.status())
              .reason(response.reason())
              .headers(response.headers());
      try {
        exceptionClientResponse.jsonBody(
            IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8.name()));
      } catch (IOException e) {
        log.error("Occurred error for convert response body", e);
      }
      return new FeignException(exceptionClientResponse.build());
    }
  }
}
