package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BaseEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@AllArgsConstructor
public class ApiRequest implements BaseEntity {

  @JsonProperty("farm_id")
  private UUID farmId;

  private List<Order> orders;

  @Getter
  @Setter
  public static class Order {

    @JsonProperty("order_start_date")
    private LocalDateTime orderStartDate;

    @JsonProperty("duration")
    private int supplyDuration;
  }
}
