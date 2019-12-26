package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DeliveryOrderNotFoundException.class)
  public ResponseEntity<Object> handleOrderNotFound(Exception ex, WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegal(Exception ex, WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    String formattedMessage =
        String.format(
            "The query parameter '%s' of value '%s' could not be converted to '%s'",
            ex.getName(),
            ex.getValue(),
            Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
    return buildResponseEntityFromApiError(HttpStatus.BAD_REQUEST, formattedMessage);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntityFromApiError(
        HttpStatus.BAD_REQUEST, "Validation error. Please check request body.");
  }

  private static ResponseEntity<Object> buildResponseEntityFromApiError(
      HttpStatus httpStatus, String message) {
    ApiError apiError = new ApiError();
    apiError.setStatus(httpStatus);
    apiError.setHttpStatusValue(httpStatus.value());
    apiError.setDateErrorOccurred(LocalDateTime.now());
    apiError.setErrorMessage(message);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
