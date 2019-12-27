package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BaseEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
public class AcceptOrderRequest implements BaseEntity {

  @JsonProperty("farm_id")
  @NotNull(message = "Please provide farm_id to create an order")
  private UUID farmId;

  @NotNull(message = "Orders must be defined")
  @NotEmpty(message = "Orders cannot be empty list")
  private List<@Valid Order> orders;

  @Getter
  @Setter
  public static class Order {

    @JsonProperty("order_start_date")
    @NotNull(message = "order_start_date must be defined")
    private LocalDateTime orderStartDate;

    @JsonProperty("duration")
    @Min(1)
    private int supplyDuration;
  }
}
