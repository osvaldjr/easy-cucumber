package example.simple.springboot.stubby.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "integration", url = "${fakeintegration.url}")
public interface IntegrationClient {

  @GetMapping(path = "/get")
  ResponseEntity<Object> get(@RequestHeader Map<String, String> headers);

  @PutMapping(path = "/put")
  ResponseEntity<Object> put(@RequestBody Object body, @RequestHeader Map<String, String> headers);

  @PostMapping(path = "/post")
  ResponseEntity<Object> post(@RequestBody Object body, @RequestHeader Map<String, String> headers);

  @DeleteMapping(path = "/delete")
  ResponseEntity<Object> delete(
      @RequestBody Object body, @RequestHeader Map<String, String> headers);

  @PatchMapping(path = "/patch")
  ResponseEntity<Object> patch(
      @RequestBody Object body, @RequestHeader Map<String, String> headers);
}
