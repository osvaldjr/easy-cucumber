package io.github.osvaldjr.utils;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import io.github.osvaldjr.clients.TargetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Optional.ofNullable;

@Component
public class HttpTarget {

  private Feign.Builder feignBuilder;

  @Autowired
  public HttpTarget(Decoder decoder, Encoder encoder, ErrorDecoder errorDecoder) {
    this.feignBuilder =
        Feign.builder()
            .decoder(decoder)
            .encoder(encoder)
            .errorDecoder(errorDecoder)
            .contract(new SpringMvcContract())
            .client(new OkHttpClient());
  }

  public ResponseEntity<Object> get(
      String host, String uri, Map<String, String> headers, Map<String, String> queryParameters) {
    return buildClient(host).get(uri, headers, queryParameters);
  }

  public <T> ResponseEntity<Object> post(
      String host,
      String uri,
      T body,
      Map<String, String> headers,
      Map<String, String> queryParameters) {
    return buildClient(host).post(uri, body, headers, queryParameters);
  }

  public <T> ResponseEntity<Object> delete(
      String host,
      String uri,
      T body,
      Map<String, String> headers,
      Map<String, String> queryParameters) {
    return requestDelete(host, uri, body, headers, queryParameters);
  }

  private <T> ResponseEntity<Object> requestDelete(
      String host,
      String uri,
      T body,
      Map<String, String> headers,
      Map<String, String> queryParameters) {
    TargetClient targetClient = buildClient(host);
    ResponseEntity<Object> delete;

    if (ofNullable(body).isPresent()) {
      delete = targetClient.delete(uri, body, headers, queryParameters);
    } else {
      delete = targetClient.delete(uri, headers, queryParameters);
    }
    return delete;
  }

  public <T> ResponseEntity<Object> put(
      String host,
      String uri,
      T body,
      Map<String, String> headers,
      Map<String, String> queryParameters) {
    return buildClient(host).put(uri, body, headers, queryParameters);
  }

  public <T> ResponseEntity<Object> patch(
      String host,
      String uri,
      T body,
      Map<String, String> headers,
      Map<String, String> queryParameters) {
    return buildClient(host).patch(uri, body, headers, queryParameters);
  }

  private TargetClient buildClient(String host) {
    return feignBuilder.target(TargetClient.class, host);
  }
}
