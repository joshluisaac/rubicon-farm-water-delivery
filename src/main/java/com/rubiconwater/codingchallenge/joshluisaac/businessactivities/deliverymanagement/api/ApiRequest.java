package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BaseEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@AllArgsConstructor
public class ApiRequest implements BaseEntity {

  @JsonProperty("farm_id")
  private UUID farmId;

  @JsonProperty("order_start_date")
  private LocalDateTime orderStartDate;

  @JsonProperty("duration")
  private int supplyDuration;
}
