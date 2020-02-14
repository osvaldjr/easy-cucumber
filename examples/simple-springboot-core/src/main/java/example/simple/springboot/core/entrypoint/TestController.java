package example.simple.springboot.core.entrypoint;

import java.util.Map;

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

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping
  public ResponseEntity<Object> get(@RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }

  @PutMapping
  public ResponseEntity<Object> put(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Object> delete(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    ResponseEntity responseEntity = ResponseEntity.badRequest().build();
    return ResponseEntity.ok().build();
  }

  @PostMapping
  public ResponseEntity<Object> post(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }

  @PatchMapping
  public ResponseEntity<Object> patch(
      @RequestBody Object body, @RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "/post/plain")
  public ResponseEntity<Object> postPlain(
      @RequestParam String myParam, @RequestHeader Map<String, String> headers) {
    return ResponseEntity.ok().build();
  }
}
