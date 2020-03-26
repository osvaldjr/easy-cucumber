package io.github.osvaldjr.core.configs;

import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import feign.Feign;
import feign.okhttp.OkHttpClient;
import io.github.osvaldjr.core.configs.converter.MessageConverter;
import io.github.osvaldjr.core.configs.decoder.FeignDecoder;
import io.github.osvaldjr.core.configs.decoder.FeignErrorDecoder;

public class FeignBuilder {

  public static Feign.Builder getClient() {
    return Feign.builder()
        .decoder(new FeignDecoder())
        .encoder(new SpringEncoder(new MessageConverter()))
        .errorDecoder(new FeignErrorDecoder())
        .contract(new SpringMvcContract())
        .client(new OkHttpClient());
  }
}
