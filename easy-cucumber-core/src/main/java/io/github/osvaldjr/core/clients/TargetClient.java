package io.github.osvaldjr.core.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "target-client")
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

  @PatchMapping(path = "{url}")
  <R> ResponseEntity<Object> patch(
      @PathVariable("url") String uri,
      @RequestBody R body,
      @RequestHeader Map<String, String> headers,
      @RequestParam Map<String, String> queryParameters);
}
