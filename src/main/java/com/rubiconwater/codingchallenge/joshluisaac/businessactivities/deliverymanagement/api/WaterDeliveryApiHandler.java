package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.Routes;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryRequest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/farmers/")
public class WaterDeliveryApiHandler {

  private final WaterDeliveryService deliveryService;

  @Autowired
  public WaterDeliveryApiHandler(WaterDeliveryService deliveryService) {
    this.deliveryService = deliveryService;
  }

  @PostMapping(value = Routes.Farmers.FARMERS)
  public ResponseEntity<ApiResponseOld> placeOrder(@RequestBody ApiRequest apiRequest) {
    deliveryService.acceptOrder(toDeliveryRequest(apiRequest));

    System.out.println(apiRequest.getFarmId());
    System.out.println(apiRequest.getOrderStartDate());
    System.out.println(apiRequest.getSupplyDuration());

    return new ResponseEntity<>(ApiResponseOld.toApiResponse(apiRequest), HttpStatus.CREATED);
  }

  @GetMapping(value = "{farmId}")
  public ResponseEntity<ApiResponse> getAllFarmOrders(@PathVariable UUID farmId) {
    List<WaterDeliveryResponse> activeOrders =
        deliveryService
            .getActiveOrders(farmId)
            .stream()
            .map(this::toDeliveryResponse)
            .collect(Collectors.toList());
    return new ResponseEntity<>(
        ApiResponse.builder()
            .waterDeliveryResponses(activeOrders)
            .status(HttpStatus.OK)
            .httpResponseCode(HttpStatus.OK.value())
            .requestDate(LocalDateTime.now())
            .build(),
        HttpStatus.OK);
  }

  @GetMapping(value = "{farmId}")
  public ResponseEntity<ApiResponse> getFarmOrder(@PathVariable UUID farmId) {
    List<WaterDeliveryResponse> activeOrders =
        deliveryService
            .getActiveOrders(farmId)
            .stream()
            .map(this::toDeliveryResponse)
            .collect(Collectors.toList());
    return new ResponseEntity<>(
        ApiResponse.builder()
            .waterDeliveryResponses(activeOrders)
            .status(HttpStatus.OK)
            .httpResponseCode(HttpStatus.OK.value())
            .requestDate(LocalDateTime.now())
            .build(),
        HttpStatus.OK);
  }

  // takes an API request and returns a delivery request
  private WaterDeliveryRequest toDeliveryRequest(ApiRequest request) {
    return WaterDeliveryRequest.builder()
        .farmId(request.getFarmId())
        .orderStartDate(request.getOrderStartDate())
        .supplyDuration(request.getSupplyDuration())
        .dateReceived(LocalDateTime.now())
        .build();
  }

  // takes in a delivery order and returns an API response
  private WaterDeliveryResponse toDeliveryResponse(WaterDeliveryRequest deliveryOrder) {
    return WaterDeliveryResponse.builder()
        .deliveryStatus(deliveryOrder.getDeliveryStatus())
        .deliveryStartDate(deliveryOrder.getTimeFrame().getStartDate())
        .deliveryEndDate(deliveryOrder.getTimeFrame().getEndDate())
        .duration(deliveryOrder.getSupplyDuration())
        .orderId(deliveryOrder.getId())
        .build();
  }
}
