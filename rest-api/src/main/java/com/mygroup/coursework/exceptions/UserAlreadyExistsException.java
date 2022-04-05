package com.mygroup.coursework.exceptions;

public final class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException() {
    super();
  }

  public UserAlreadyExistsException(final String message) {
    super(message);
  }

  public UserAlreadyExistsException(final Throwable cause) {
    super(cause);
  }

  public UserAlreadyExistsException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
