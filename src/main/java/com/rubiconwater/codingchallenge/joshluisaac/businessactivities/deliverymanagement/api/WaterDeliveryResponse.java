package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class WaterDeliveryResponse {

  @JsonProperty(value = "order_id")
  private UUID orderId;

  @JsonProperty(value = "delivery_status")
  private WaterDeliveryStatus deliveryStatus;

  @JsonProperty("delivery_start_date")
  private LocalDateTime deliveryStartDate;

  @JsonProperty("delivery_end_date")
  private LocalDateTime deliveryEndDate;

  private int duration;
}
