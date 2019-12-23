package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryOrder;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiUtils {

  public static ResponseEntity<ApiResponse> buildResponseEntity(
      List<WaterDeliveryResponse> activeOrders, HttpStatus status) {
    return new ResponseEntity<>(
        ApiResponse.builder()
            .waterDeliveryResponses(activeOrders)
            .status(status.getReasonPhrase())
            .httpResponseCode(status.value())
            .requestDate(LocalDateTime.now())
            .build(),
        status);
  }

  // takes an API request and returns a delivery request
  public static WaterDeliveryOrder toDeliveryOrder(
      AcceptOrderRequest request, AcceptOrderRequest.Order order) {
    return WaterDeliveryOrder.builder()
        .farmId(request.getFarmId())
        .orderStartDate(order.getOrderStartDate())
        .supplyDuration(order.getSupplyDuration())
        .dateReceived(LocalDateTime.now())
        .build();
  }

  // takes in a delivery order and returns an API response
  public static WaterDeliveryResponse toDeliveryResponse(WaterDeliveryOrder deliveryOrder) {
    return WaterDeliveryResponse.builder()
        .deliveryStatus(deliveryOrder.getDeliveryStatus().getDescription())
        .deliveryStartDate(deliveryOrder.getTimeFrame().getStartDate())
        .deliveryEndDate(deliveryOrder.getTimeFrame().getEndDate())
        .duration(deliveryOrder.getSupplyDuration())
        .orderId(deliveryOrder.getId())
        .build();
  }
}