package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.google.common.base.Preconditions;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.AbstractEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Getter
public class WaterDeliveryRequest extends AbstractEntity {

  private UUID farmId;
  private LocalDateTime dateReceived;
  private LocalDateTime orderStartDate;
  private int supplyDuration;
  @Setter private WaterDeliveryStatus deliveryStatus;
  private String hash;

  @Builder
  public WaterDeliveryRequest(
      UUID farmId,
      LocalDateTime dateReceived,
      LocalDateTime orderStartDate,
      int supplyDuration,
      WaterDeliveryStatus deliveryStatus) {
    super(UuidUtils.create());
    checkOrderStartDate(dateReceived, orderStartDate);
    checkSupplyDuration(supplyDuration);
    this.farmId = farmId;
    this.dateReceived = dateReceived;
    this.orderStartDate = orderStartDate;
    this.supplyDuration = supplyDuration;
    this.deliveryStatus = deliveryStatus;
    this.hash = calculateHash();
  }

  private void checkOrderStartDate(LocalDateTime dateReceived, LocalDateTime orderStartDate) {
    Preconditions.checkArgument(
        (orderStartDate.isAfter(dateReceived) || orderStartDate.isEqual(dateReceived)),
        "Order start date cannot be in the past.");
  }

  private void checkSupplyDuration(int supplyDuration) {
    Preconditions.checkArgument(
        supplyDuration > 0, "Request supply duration cannot be less than 0");
  }

  private String calculateHash() {
    return "";
  }

  public boolean isBetweenTimeFrameOf(WaterDeliveryRequest requestOrder) {
    return true;
  }
}
