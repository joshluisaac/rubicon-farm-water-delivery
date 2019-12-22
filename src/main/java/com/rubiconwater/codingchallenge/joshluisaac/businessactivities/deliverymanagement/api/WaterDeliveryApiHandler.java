package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryService;
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
  public ResponseEntity<ApiResponseOld> placeOrder(@RequestBody ApiRequest apiRequest) {
    deliveryService.acceptOrder(ApiUtils.toDeliveryRequest(apiRequest));

    System.out.println(apiRequest.getFarmId());
    System.out.println(apiRequest.getOrderStartDate());
    System.out.println(apiRequest.getSupplyDuration());

    return new ResponseEntity<>(ApiResponseOld.toApiResponse(apiRequest), HttpStatus.CREATED);
  }

  @GetMapping(value = "farmers/{farmId}")
  public ResponseEntity<ApiResponse> getAllFarmOrders(@PathVariable UUID farmId) {
    List<WaterDeliveryResponse> activeOrders =
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
