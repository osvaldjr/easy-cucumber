package io.github.osvaldjr.gateways.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "target-client")
@Component
public interface TargetClient {

  @GetMapping(path = "{url}")
  ResponseEntity<Object> get(
      @PathVariable("url") String url,
      @RequestHeader Map<String, String> headers,
      @RequestParam Map<String, String> queryParameters);

  @PostMapping(path = "{url}")
  <R> ResponseEntity<Object> post(
      @PathVariable("url") String uri,
      @RequestBody R body,
      @RequestHeader Map<String, String> headers,
      @RequestParam Map<String, String> queryParameters);

  @DeleteMapping(path = "{url}")
  <R> ResponseEntity<Object> delete(
      @PathVariable("url") String uri,
      @RequestBody R body,
      @RequestHeader Map<String, String> headers,
      @RequestParam Map<String, String> queryParameters);

  @DeleteMapping(path = "{url}")
  ResponseEntity<Object> delete(
      @PathVariable("url") String uri,
      @RequestHeader Map<String, String> headers,
      @RequestParam Map<String, String> queryParameters);

  @PutMapping(path = "{url}")
  <R> ResponseEntity<Object> put(
      @PathVariable("url") String uri,
      @RequestBody R body,
      @RequestHeader Map<String, String> headers,
      @RequestParam Map<String, String> queryParameters);
}
