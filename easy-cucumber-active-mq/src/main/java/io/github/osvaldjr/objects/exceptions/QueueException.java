package io.github.osvaldjr.objects.exceptions;

import lombok.Getter;

@Getter
public class QueueException extends RuntimeException {

  public QueueException(String message) {
    super(message);
  }
}
