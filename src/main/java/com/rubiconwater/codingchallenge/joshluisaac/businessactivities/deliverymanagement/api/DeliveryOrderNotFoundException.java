package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.Errors;
import java.util.UUID;

public class DeliveryOrderNotFoundException extends RuntimeException {

  public DeliveryOrderNotFoundException(UUID id) {
    super(String.format("%s : '%s'", Errors.ORDER_NOT_FOUND.getDescription(), id));
  }
}
