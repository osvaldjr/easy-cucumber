package io.github.osvaldjr.gateways.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import feign.Body;
import feign.HeaderMap;
import feign.Param;
import feign.RequestLine;

@FeignClient(value = "target-client")
public interface TargetClient {

  @RequestLine("GET {url}")
  ResponseEntity<Object> get(@Param("url") String url, @HeaderMap Map<String, String> headers);

  @RequestLine("POST {url}")
  @Body("{body}")
  <R> ResponseEntity<Object> post(
      @Param("url") String uri, R body, @HeaderMap Map<String, String> headers);

  @RequestLine("DELETE {url}")
  @Body("{body}")
  <R> ResponseEntity<Object> delete(
      @Param("url") String uri, R body, @HeaderMap Map<String, String> headers);

  @RequestLine("DELETE {url}")
  ResponseEntity<Object> delete(@Param("url") String uri, @HeaderMap Map<String, String> headers);

  @RequestLine("PUT {url}")
  @Body("{body}")
  <R> ResponseEntity<Object> put(
      @Param("url") String uri, R body, @HeaderMap Map<String, String> headers);
}
