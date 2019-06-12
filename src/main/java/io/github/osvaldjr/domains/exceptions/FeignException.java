package io.github.osvaldjr.domains.exceptions;

import feign.Response;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {

  private Response response;

  public FeignException(Response response) {
    this.response = response;
  }

  public FeignException(String message, Response response) {
    super(message);
    this.response = response;
  }

  public FeignException(String message, Throwable cause, Response response) {
    super(message, cause);
    this.response = response;
  }

  public FeignException(Throwable cause, Response response) {
    super(cause);
    this.response = response;
  }

  public FeignException(
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace,
      Response response) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.response = response;
  }
}
