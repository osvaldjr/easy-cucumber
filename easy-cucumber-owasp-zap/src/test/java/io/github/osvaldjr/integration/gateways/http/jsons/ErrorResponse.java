package io.github.osvaldjr.integration.gateways.http.jsons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorResponse implements Serializable {

  private static final long serialVersionUID = 7063090178203797801L;

  public static final String INTERNAL_SERVER_ERROR = "error.internalServerError";

  private final String message;

  public ErrorResponse(String message) {
    this.message = message;
  }

  public static ErrorResponse build(String message) {
    return new ErrorResponse(message);
  }
}
