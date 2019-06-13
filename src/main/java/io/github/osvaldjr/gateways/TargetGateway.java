package io.github.osvaldjr.gateways;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.feign.TargetClient;

@Component
public class TargetGateway {

  private final TargetClient feignClient;

  @Autowired
  public TargetGateway(TargetClient feignClient) {
    this.feignClient = feignClient;
  }

  public <R> ResponseEntity<R> get(String uri, Map<String, String> headers) {
    return feignClient.get(uri, headers);
  }

  public <T, R> ResponseEntity<R> post(String uri, T body, Map<String, String> headers) {
    return feignClient.post(uri, body, headers);
  }

  public <T, R> ResponseEntity<R> delete(String uri, T body, Map<String, String> headers) {
    return feignClient.delete(uri, body, headers);
  }

  public <T, R> ResponseEntity<R> put(String uri, T body, Map<String, String> headers) {
    return feignClient.put(uri, body, headers);
  }
}
