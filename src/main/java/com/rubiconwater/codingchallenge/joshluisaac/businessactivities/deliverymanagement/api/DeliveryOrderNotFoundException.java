package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import java.util.UUID;

public class DeliveryOrderNotFoundException extends RuntimeException {

  public DeliveryOrderNotFoundException(UUID id) {
    super(String.format("Delivery order not found: (%s)", id));
  }
}
