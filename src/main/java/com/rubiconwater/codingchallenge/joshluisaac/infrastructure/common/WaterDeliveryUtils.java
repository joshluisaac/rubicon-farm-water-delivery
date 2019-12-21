package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WaterDeliveryUtils {

  public static String toIsoLocalDateTime(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}
