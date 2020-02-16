package example.complete.springboot.entrypoint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import example.complete.springboot.entrypoint.json.Error;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Error handleException(Exception exception) {
    exception.printStackTrace();
    return Error.build(Error.INTERNAL_SERVER_ERROR);
  }
}
