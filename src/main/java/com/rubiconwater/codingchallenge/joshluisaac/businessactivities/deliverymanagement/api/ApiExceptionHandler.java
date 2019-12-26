package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.Errors;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DeliveryOrderNotFoundException.class)
  public ResponseEntity<Object> handleOrderNotFound(Exception ex, WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegal(Exception ex, WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    String formattedMessage =
        String.format(
            Errors.METHOD_ARGUMENT_TYPE_MISMATCH.getDescription(),
            ex.getName(),
            ex.getValue(),
            Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
    return buildResponseEntityFromApiError(HttpStatus.BAD_REQUEST, formattedMessage, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntityFromApiError(
        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        String.format("%s. Please use '%s'", ex.getMessage(), MediaType.APPLICATION_JSON),
        request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(entry -> System.out.println(entry.getDefaultMessage()));
    return buildResponseEntityFromApiError(
        HttpStatus.BAD_REQUEST,
        Errors.REQUEST_BODY_DESERIALIZATION_ERROR.getDescription(),
        request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntityFromApiError(
        HttpStatus.BAD_REQUEST,
        Errors.REQUEST_BODY_DESERIALIZATION_ERROR.getDescription(),
        request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntityFromApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), request);
  }

  private static ResponseEntity<Object> buildResponseEntityFromApiError(
      HttpStatus httpStatus, String message, WebRequest request) {
    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    ApiError apiError = new ApiError();
    apiError.setStatus(httpStatus);
    apiError.setHttpStatusValue(httpStatus.value());
    apiError.setDateErrorOccurred(LocalDateTime.now());
    apiError.setErrorMessage(message);
    apiError.setPath(servletWebRequest.getRequest().getServletPath());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
