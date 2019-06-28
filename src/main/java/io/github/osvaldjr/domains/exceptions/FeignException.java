package io.github.osvaldjr.domains.exceptions;

import io.github.osvaldjr.domains.ClientResponse;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {

  private final ClientResponse response;

  public FeignException(ClientResponse response) {
    this.response = response;
  }

  public FeignException(String message, ClientResponse response) {
    super(message);
    this.response = response;
  }

  public FeignException(String message, Throwable cause, ClientResponse response) {
    super(message, cause);
    this.response = response;
  }

  public FeignException(Throwable cause, ClientResponse response) {
    super(cause);
    this.response = response;
  }

  public FeignException(
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace,
      ClientResponse response) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.response = response;
  }
}
