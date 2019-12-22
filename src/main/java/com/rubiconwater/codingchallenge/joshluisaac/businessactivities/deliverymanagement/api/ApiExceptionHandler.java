package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private ResponseEntity<Object> buildResponseEntityFromApiError(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
