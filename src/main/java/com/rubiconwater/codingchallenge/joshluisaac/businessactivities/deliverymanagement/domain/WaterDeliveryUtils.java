package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WaterDeliveryUtils {

  private WaterDeliveryUtils() {}

  public static boolean isNotCancelled(WaterDeliveryOrder entry) {
    return (entry.getDeliveryStatus() != WaterDeliveryStatus.CANCELLED);
  }

  public static boolean isPastDeliveryDueDate(
      WaterDeliveryOrder requestOrder, LocalDateTime batchDate) {
    return requestOrder.getTimeFrame().getEndDate().isBefore(batchDate);
  }

  public static boolean isRequested(WaterDeliveryOrder requestOrder) {
    return (requestOrder.getDeliveryStatus() == WaterDeliveryStatus.REQUESTED);
  }

  public static boolean isInProgress(WaterDeliveryOrder requestOrder) {
    return (requestOrder.getDeliveryStatus() == WaterDeliveryStatus.IN_PROGRESS);
  }

  public static String toIsoLocalDateTime(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}
