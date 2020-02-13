package io.github.osvaldjr.mocks.objects.exceptions;

import io.github.osvaldjr.mocks.objects.ClientResponse;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {

  private final io.github.osvaldjr.mocks.objects.ClientResponse response;

  public FeignException(io.github.osvaldjr.mocks.objects.ClientResponse response) {
    this.response = response;
  }

  public FeignException(String message, io.github.osvaldjr.mocks.objects.ClientResponse response) {
    super(message);
    this.response = response;
  }

  public FeignException(
      String message, Throwable cause, io.github.osvaldjr.mocks.objects.ClientResponse response) {
    super(message, cause);
    this.response = response;
  }

  public FeignException(Throwable cause, io.github.osvaldjr.mocks.objects.ClientResponse response) {
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
