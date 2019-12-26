package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

// @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Crazy request....")
public class BadRequestException extends RuntimeException {

  public BadRequestException() {
    super("Bad request. Please check query string or request body");
  }
}
