package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryOrder;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class WaterDeliveryApiHandler {

  private final WaterDeliveryService deliveryService;

  @Autowired
  public WaterDeliveryApiHandler(WaterDeliveryService deliveryService) {
    this.deliveryService = deliveryService;
  }

  @PostMapping(value = "farmers")
  public ResponseEntity<ApiResponse> placeOrder(@RequestBody ApiRequest apiRequest) {
    List<WaterDeliveryOrder> orders = new ArrayList<>();
    apiRequest
        .getOrders()
        .forEach(
            entry -> {
              var deliveryOrder =
                  deliveryService.acceptOrder(ApiUtils.toDeliveryOrder(apiRequest, entry));
              orders.add(deliveryOrder);
            });
    var activeOrders =
        orders.stream().map(ApiUtils::toDeliveryResponse).collect(Collectors.toUnmodifiableList());
    return ApiUtils.buildResponseEntity(activeOrders, HttpStatus.CREATED);
  }

  @GetMapping(value = "farmers/{farmId}")
  public ResponseEntity<ApiResponse> getAllFarmOrders(@PathVariable UUID farmId) {
    var activeOrders =
        deliveryService
            .getActiveOrders(farmId)
            .stream()
            .map(ApiUtils::toDeliveryResponse)
            .collect(Collectors.toUnmodifiableList());
    return ApiUtils.buildResponseEntity(activeOrders, HttpStatus.OK);
  }

  @GetMapping(value = "farmers/{farmId}/orders/{orderId}")
  public ResponseEntity<ApiResponse> getFarmOrder(
      @PathVariable UUID farmId, @PathVariable UUID orderId) {
    return ApiUtils.buildResponseEntity(
        List.of(ApiUtils.toDeliveryResponse(deliveryService.getActiveOrder(farmId, orderId))),
        HttpStatus.OK);
  }
}
