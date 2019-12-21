package com.rubiconwater.codingchallenge.joshluisaac.businessactivities;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryRequest;
import java.time.LocalDateTime;
import java.util.UUID;

public interface AbstractTest {

  default WaterDeliveryRequest createOrderRequest(
      UUID farmId,
      LocalDateTime dateOrderReceived,
      LocalDateTime orderStartDate,
      int supplyDuration) {
    return WaterDeliveryRequest.builder()
        .farmId(farmId)
        .dateReceived(dateOrderReceived)
        .orderStartDate(orderStartDate)
        .supplyDuration(supplyDuration)
        .build();
  }
}
