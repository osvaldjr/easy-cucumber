package br.community.component.test.gateways.http;

import java.util.Map;

import org.ff4j.spring.autowire.FF4JFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.community.component.test.gateways.feign.IntegrationClient;

@RestController
@RequestMapping("/test")
public class IntegrationController {

  @FF4JFeature("delete-integration")
  protected boolean isDeleteIntegration;

  private final IntegrationClient client;

  @Autowired
  public IntegrationController(IntegrationClient client) {
    this.client = client;
  }

  @GetMapping
  public ResponseEntity get(@RequestHeader Map<String, String> headers) {
    return client.get(headers);
  }

  @PutMapping
  public void put(@RequestBody Object body, @RequestHeader Map<String, String> headers) {
    client.put(body, headers);
  }

  @DeleteMapping
  public ResponseEntity delete(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    ResponseEntity responseEntity = ResponseEntity.badRequest().build();
    if (isDeleteIntegration) {
      responseEntity = client.delete(body, headers);
    }
    return responseEntity;
  }

  @PostMapping
  public ResponseEntity post(@RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return client.post(body, headers);
  }
}
