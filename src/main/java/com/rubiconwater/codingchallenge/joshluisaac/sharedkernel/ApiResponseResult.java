package com.rubiconwater.codingchallenge.joshluisaac.sharedkernel;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiResponseResult {

  private final HttpStatus httpStatus;
  private final String message;
  private final Object beanHolder;
  private final ApiResponseFlagType flagType;

  public ApiResponseResult(
      Object beanHolder, ApiResponseFlagType flagType, HttpStatus httpStatus, String message) {
    this.beanHolder = beanHolder;
    this.flagType = flagType;
    this.httpStatus = httpStatus;
    this.message = message;
  }

  private static ApiResponseResult create(
      Object beanHolder, ApiResponseFlagType flagType, HttpStatus httpStatus, String message) {
    return new ApiResponseResult(beanHolder, flagType, httpStatus, message);
  }

  private static ApiResponseResult createSuccess(Object beanHolder) {
    return create(
        beanHolder, ApiResponseFlagType.SUCCESS, HttpStatus.OK, HttpStatus.OK.getReasonPhrase());
  }

  private static ApiResponseResult createFailed(Object beanHolder) {
    return create(
        beanHolder,
        ApiResponseFlagType.ERROR,
        HttpStatus.OK,
        "Request was processed but could not return result.");
  }

  public static <T extends BaseEntity> ApiResponseResult toApiResponse(T t) {
    if (t != null) {
      return createSuccess(t);
    }
    return createFailed("Invalid request. Please check URL parameters.");
  }

  HttpStatus getHttpStatus() {
    return httpStatus;
  }

  String getMessage() {
    return message;
  }

  Object getBeanHolder() {
    return beanHolder;
  }

  ApiResponseFlagType getFlagType() {
    return flagType;
  }
}
