package io.github.osvaldjr.integration.gateways.http;

import java.util.Map;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import io.github.osvaldjr.integration.gateways.feign.IntegrationClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/test")
@Api(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

  private final FF4j ff4j;

  private final IntegrationClient client;

  @Autowired
  public TestController(FF4j ff4j, IntegrationClient client) {
    this.ff4j = ff4j;
    this.client = client;
  }

  @ApiOperation(value = "get")
  @GetMapping
  public ResponseEntity<Object> get(
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return client.get(headers);
  }

  @ApiOperation(value = "put")
  @PutMapping
  public ResponseEntity<Object> put(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return client.put(body, headers);
  }

  @ApiOperation(value = "delete")
  @DeleteMapping
  public ResponseEntity<Object> delete(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    ResponseEntity responseEntity = ResponseEntity.badRequest().build();
    if (ff4j.check("delete-integration")) {
      responseEntity = client.delete(body, headers);
    }
    return responseEntity;
  }

  @ApiOperation(value = "post")
  @PostMapping
  public ResponseEntity<Object> post(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return client.post(body, headers);
  }

  @ApiOperation(value = "patch")
  @PatchMapping
  public ResponseEntity<Object> patch(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return client.patch(body, headers);
  }

  @ApiOperation(value = "post/plain")
  @PostMapping(path = "/post/plain")
  public ResponseEntity<Object> postPlain(
      @ApiParam(value = "myParam") @RequestParam String myParam,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return client.post("myParam=" + myParam, headers);
  }
}
