package io.github.osvaldjr.domains.exceptions;

import lombok.Getter;

@Getter
public class QueueException extends RuntimeException {

  public QueueException(String message) {
    super(message);
  }
}
