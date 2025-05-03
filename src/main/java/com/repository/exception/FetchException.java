package com.repository.exception;

public class FetchException extends RuntimeException {
  public FetchException() {
  }

  public FetchException(String message, Throwable cause) {
    super(message, cause);
  }

  public FetchException(String message) {
    super(message);
  }

  public FetchException(Throwable cause) {
    super(cause);
  }
}
