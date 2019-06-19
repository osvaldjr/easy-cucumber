package io.github.osvaldjr.integration.gateways.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.osvaldjr.integration.gateways.http.jsons.ErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse handleException(Exception exception) {
    return ErrorResponse.build(ErrorResponse.INTERNAL_SERVER_ERROR);
  }
}
