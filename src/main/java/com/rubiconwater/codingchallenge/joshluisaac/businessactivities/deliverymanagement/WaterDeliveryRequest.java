package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.google.common.base.Preconditions;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.AbstractEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.*;
import org.springframework.util.DigestUtils;

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
    this.hash = createHash();
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

  private LocalDateTime deliveryEndDate() {
    return orderStartDate.plusHours(supplyDuration);
  }

  private String createHash() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
        .append(farmId.toString())
        .append(toIsoLocalDateTime(orderStartDate))
        .append(toIsoLocalDateTime(deliveryEndDate()));
    return DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
  }

  //pass in the time frame reference and not requestOrder
  public boolean isBetweenTimeFrameOf(WaterDeliveryRequest requestOrder) {
    System.out.println(getTimeFrame().getStartDate());
    System.out.println(getTimeFrame().getEndDate());

    System.out.println(requestOrder.getTimeFrame().getStartDate());
    System.out.println(requestOrder.getTimeFrame().getEndDate());


    LocalDateTime newOrderStartDate = requestOrder.getTimeFrame().getStartDate();
    LocalDateTime newOrderEndDate = requestOrder.getTimeFrame().getEndDate();

   boolean isBetween =  (newOrderStartDate.isEqual(orderStartDate) || newOrderStartDate.isAfter(orderStartDate)) && (newOrderStartDate.isBefore(deliveryEndDate()) || newOrderStartDate.isEqual(deliveryEndDate()));


    return true;
  }

  private static String toIsoLocalDateTime(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public TimeFrame getTimeFrame() {
    return TimeFrame.builder().startDate(orderStartDate).endDate(deliveryEndDate()).build();
  }


  public boolean isAfterOrEqualToOrderStartDate(WaterDeliveryRequest requestOrder){
    LocalDateTime newOrderStartDate = requestOrder.getTimeFrame().getStartDate();
    return (newOrderStartDate.isEqual(orderStartDate) || newOrderStartDate.isAfter(orderStartDate));
  }

  public boolean isBetweenOrderEndDate(WaterDeliveryRequest requestOrder){
    LocalDateTime newOrderStartDate = requestOrder.getTimeFrame().getStartDate();
    return (newOrderStartDate.isBefore(deliveryEndDate()) || newOrderStartDate.isEqual(deliveryEndDate()));
  }


}
