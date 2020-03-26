package example.simple.springboot.stubby.entrypoint;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.simple.springboot.stubby.client.IntegrationClient;

@RestController
@RequestMapping("/test")
public class TestController {

  private final IntegrationClient client;

  @Autowired
  public TestController(IntegrationClient client) {
    this.client = client;
  }

  @GetMapping
  public ResponseEntity<Object> get(@RequestHeader Map<String, String> headers) {
    return client.get(headers);
  }

  @PutMapping
  public ResponseEntity<Object> put(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return client.put(body, headers);
  }

  @DeleteMapping
  public ResponseEntity<Object> delete(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return ResponseEntity.badRequest().build();
  }

  @PostMapping
  public ResponseEntity<Object> post(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return client.post(body, headers);
  }

  @PatchMapping
  public ResponseEntity<Object> patch(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return client.patch(body, headers);
  }

  @PostMapping(path = "/post/plain")
  public ResponseEntity<Object> postPlain(
      @RequestParam String myParam, @RequestHeader Map<String, String> headers) {
    return client.post("myParam=" + myParam, headers);
  }
}
