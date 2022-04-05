package com.mygroup.coursework.gui.exceptions;

public class UnknownCommandException extends RuntimeException {

  public UnknownCommandException() {
    super();
  }

  public UnknownCommandException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnknownCommandException(String message) {
    super(message);
  }

  public UnknownCommandException(Throwable cause) {
    super(cause);
  }

}
