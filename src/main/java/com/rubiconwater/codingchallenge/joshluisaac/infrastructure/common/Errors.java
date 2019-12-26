package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

public class Errors {

  private Errors() {}

  // service layer errors
  public static final Error ORDER_DURATION_CONSTRAINT =
      new Error("ORDER_DURATION_CONSTRAINT", "Delivery supply duration cannot be less than 0");

  public static final Error TIME_FRAME_COLLISION =
      new Error(
          "TIME_FRAME_COLLISION",
          "The requested order falls within the time frame of another order");

  public static final Error EXISTING_ORDER_DUPLICATION =
      new Error("EXISTING_ORDER_DUPLICATION", "The requested order exists.");

  public static final Error ORDER_NOT_FOUND = new Error("ORDER_NOT_FOUND", "Order not found.");

  public static final Error FARM_ID_NOT_FOUND =
      new Error("FARM_ID_NOT_FOUND", "Farm id '%s' not found.");

  public static final Error CANCEL_ORDER_NOT_ALLOWED =
      new Error(
          "CANCEL_ORDER_NOT_ALLOWED",
          "Cancel order operation not allowed. "
              + "You cannot cancel what has already been delivered or previously cancelled. "
              + "Please check order delivery status for orderId (%s)");

  public static final Error ORDER_START_DATE_IN_THE_PAST =
      new Error(
          "ORDER_START_DATE_IN_THE_PAST",
          "Order start date cannot be in the past. Please check '%s'");

  // Web layer errors
  public static final Error REQUEST_BODY_DESERIALIZATION_ERROR =
      new Error(
          "REQUEST_BODY_DESERIALIZATION_ERROR", "Validation error. Please check request body.");

  public static final Error METHOD_ARGUMENT_TYPE_MISMATCH =
      new Error(
          "METHOD_ARGUMENT_TYPE_MISMATCH",
          "The parameter '%s' of value '%s' could not be converted to '%s'");
}
