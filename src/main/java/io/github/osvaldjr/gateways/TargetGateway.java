package io.github.osvaldjr.gateways;

import static java.util.Optional.ofNullable;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import io.github.osvaldjr.gateways.feign.TargetClient;

@Component
public class TargetGateway {

  private Feign.Builder feignBuilder;

  @Autowired
  public TargetGateway(
      Decoder decoder, Encoder encoder, Contract contract, ErrorDecoder errorDecoder) {
    this.feignBuilder =
        Feign.builder()
            .decoder(decoder)
            .encoder(encoder)
            .errorDecoder(errorDecoder)
            .contract(contract);
  }

  public ResponseEntity<Object> get(String host, String uri, Map<String, String> headers) {
    return buildClient(host).get(uri, headers);
  }

  public <T> ResponseEntity<Object> post(
      String host, String uri, T body, Map<String, String> headers) {
    return buildClient(host).post(uri, body, headers);
  }

  public <T> ResponseEntity<Object> delete(
      String host, String uri, T body, Map<String, String> headers) {
    return requestDelete(host, uri, body, headers);
  }

  private <T> ResponseEntity<Object> requestDelete(
      String host, String uri, T body, Map<String, String> headers) {
    TargetClient targetClient = buildClient(host);
    ResponseEntity<Object> delete;

    if (ofNullable(body).isPresent()) {
      delete = targetClient.delete(uri, body, headers);
    } else {
      delete = targetClient.delete(uri, headers);
    }
    return delete;
  }

  public <T> ResponseEntity<Object> put(
      String host, String uri, T body, Map<String, String> headers) {
    return buildClient(host).put(uri, body, headers);
  }

  private TargetClient buildClient(String host) {
    return feignBuilder.target(TargetClient.class, host);
  }
}
