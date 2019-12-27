package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryOrder;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiMapper {

  public static ResponseEntity<ApiResponse> buildResponseEntity(
      List<WaterDeliveryResponse> activeOrders, HttpStatus status) {
    ApiResponse apiResponse =
        new ApiResponse(
            status.getReasonPhrase(), status.value(), LocalDateTime.now(), activeOrders);
    return new ResponseEntity<>(apiResponse, status);
  }

  // takes an API request and returns a delivery request
  public WaterDeliveryOrder toDeliveryOrder(
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
    return new WaterDeliveryResponse(
        deliveryOrder.getId(),
        deliveryOrder.getDeliveryStatus().getDescription(),
        deliveryOrder.getTimeFrame().getStartDate(),
        deliveryOrder.getTimeFrame().getEndDate(),
        deliveryOrder.getSupplyDuration());
  }
}
