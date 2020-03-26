package example.simple.springboot.owaspzap.entrypoint.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

  public static final String INTERNAL_SERVER_ERROR = "error.internalServerError";

  private final String message;

  public Error(String message) {
    this.message = message;
  }

  public static Error build(String message) {
    return new Error(message);
  }

  public String getMessage() {
    return message;
  }
}
