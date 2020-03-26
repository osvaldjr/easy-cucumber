package io.github.osvaldjr.core.utils;

import static java.util.Optional.ofNullable;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.Feign;
import io.github.osvaldjr.core.clients.TargetClient;
import io.github.osvaldjr.core.configs.FeignBuilder;

@Component
public class HttpTarget {

  private Feign.Builder feignBuilder;

  public HttpTarget() {
    this.feignBuilder = FeignBuilder.getClient();
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
