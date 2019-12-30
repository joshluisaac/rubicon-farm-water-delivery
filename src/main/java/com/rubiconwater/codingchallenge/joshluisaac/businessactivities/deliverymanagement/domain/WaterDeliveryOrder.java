package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import com.google.common.base.Preconditions;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.Errors;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.AbstractEntity;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.util.DigestUtils;

@Getter
public class WaterDeliveryOrder extends AbstractEntity {

  private UUID farmId;
  private LocalDateTime dateReceived;
  private LocalDateTime orderStartDate;
  private int supplyDuration;
  @Setter private WaterDeliveryStatus deliveryStatus;
  private String hash;

  @Builder
  @ConstructorProperties({"farmId", "dateReceived", "orderStartDate", "supplyDuration"})
  public WaterDeliveryOrder(
      UUID farmId, LocalDateTime dateReceived, LocalDateTime orderStartDate, int supplyDuration) {
    super(UuidUtils.create());
    checkOrderStartDate(dateReceived, orderStartDate);
    checkSupplyDuration(supplyDuration);
    this.farmId = farmId;
    this.dateReceived = dateReceived;
    this.orderStartDate = orderStartDate;
    this.supplyDuration = supplyDuration;
    this.deliveryStatus = WaterDeliveryStatus.REQUESTED;
    this.hash = createHash();
  }

  private void checkOrderStartDate(LocalDateTime dateReceived, LocalDateTime orderStartDate) {
    Preconditions.checkArgument(
        (orderStartDate.isAfter(dateReceived) || orderStartDate.isEqual(dateReceived)),
        String.format(Errors.ORDER_START_DATE_IN_THE_PAST.getDescription(), orderStartDate, dateReceived));
  }

  private void checkSupplyDuration(int supplyDuration) {
    Preconditions.checkArgument(
        supplyDuration > 0, Errors.ORDER_DURATION_CONSTRAINT.getDescription());
  }

  private LocalDateTime deliveryEndDate() {
    return orderStartDate.plusHours(supplyDuration);
  }

  private String createHash() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
        .append(farmId.toString())
        .append(WaterDeliveryUtils.toIsoLocalDateTime(orderStartDate))
        .append(WaterDeliveryUtils.toIsoLocalDateTime(deliveryEndDate()));
    return DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
  }

  public DeliveryTimeWindow getTimeFrame() {
    return new DeliveryTimeWindow(orderStartDate, deliveryEndDate());
  }
}
