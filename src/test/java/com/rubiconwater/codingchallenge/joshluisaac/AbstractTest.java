package com.rubiconwater.codingchallenge.joshluisaac;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryOrder;
import java.time.LocalDateTime;
import java.util.UUID;

public interface AbstractTest {

  default WaterDeliveryOrder createOrderRequest(
      UUID farmId,
      LocalDateTime dateOrderReceived,
      LocalDateTime orderStartDate,
      int supplyDuration) {
    return WaterDeliveryOrder.builder()
        .farmId(farmId)
        .dateReceived(dateOrderReceived)
        .orderStartDate(orderStartDate)
        .supplyDuration(supplyDuration)
        .build();
  }
}
