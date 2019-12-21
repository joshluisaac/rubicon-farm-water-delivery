package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ApiDeliveryRequest {

  @JsonProperty("farm_id")
  private UUID farmId;

  @JsonProperty("order_start_date")
  private LocalDateTime orderStartDate;

  @JsonProperty("duration")
  private int supplyDuration;
}
