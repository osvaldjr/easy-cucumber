package example.simple.springboot.owaspzap.entrypoint;

import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/test")
@Api(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

  private final ObjectMapper objectMapper;

  public TestController(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @ApiOperation(value = "get")
  @GetMapping
  public ResponseEntity<Object> get(
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers)
      throws JsonProcessingException {
    return ResponseEntity.ok(
        objectMapper.readValue("{\"name\": \"Linux\",\"developer\": true}", Object.class));
  }

  @ApiOperation(value = "get error")
  @GetMapping("/error")
  public ResponseEntity<Object> getError(
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) throws Exception {
    throw new Exception("error");
  }

  @ApiOperation(value = "put")
  @PutMapping
  public ResponseEntity<Object> put(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "delete")
  @DeleteMapping
  public ResponseEntity<Object> delete(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    ResponseEntity responseEntity = ResponseEntity.badRequest().build();
    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "post")
  @PostMapping
  public ResponseEntity<Object> post(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers)
      throws JsonProcessingException {
    return ResponseEntity.ok(
        objectMapper.readValue(
            "{\"name\": \"Linux\",\"developer\": true,\"year\": 2000}", Object.class));
  }

  @ApiOperation(value = "patch")
  @PatchMapping
  public ResponseEntity<Object> patch(
      @ApiParam(value = "body") @RequestBody Object body,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "post plain")
  @PostMapping(path = "/post/plain")
  public ResponseEntity<Object> postPlain(
      @ApiParam(value = "myParam") @RequestParam String myParam,
      @ApiParam(value = "headers") @RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }
}
