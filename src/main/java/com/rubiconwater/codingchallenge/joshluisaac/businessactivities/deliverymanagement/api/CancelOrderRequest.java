package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BaseEntity;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelOrderRequest implements BaseEntity {

  @JsonProperty("farm_id")
  @NotNull(message = "Please provide farm_id to cancel order")
  private UUID farmId;

  @JsonProperty("orders")
  private List<UUID> deliveryIds;
}
