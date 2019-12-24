package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MyResourceNotFoundException extends RuntimeException {
  public MyResourceNotFoundException() {
    super();
  }

  public MyResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public MyResourceNotFoundException(String message) {
    super(message);
  }

  public MyResourceNotFoundException(Throwable cause) {
    super(cause);
  }
}
