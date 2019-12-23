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

  /**
   * Controller endpoint to place an order.
   *
   * @param acceptOrderRequest
   * @return
   */
  @PostMapping(value = "farmers")
  public ResponseEntity<ApiResponse> placeOrder(
      @RequestBody AcceptOrderRequest acceptOrderRequest) {
    List<WaterDeliveryOrder> orders = new ArrayList<>();
    acceptOrderRequest
        .getOrders()
        .forEach(
            entry -> {
              var deliveryOrder =
                  deliveryService.acceptOrder(ApiUtils.toDeliveryOrder(acceptOrderRequest, entry));
              orders.add(deliveryOrder);
            });
    var activeOrders =
        orders.stream().map(ApiUtils::toDeliveryResponse).collect(Collectors.toUnmodifiableList());
    return ApiUtils.buildResponseEntity(activeOrders, HttpStatus.CREATED);
  }

  /**
   * Controller endpoint to cancel an order.
   *
   * @param cancelOrderRequest
   * @param cancel
   * @return
   */
  @PutMapping(value = "farmers")
  public ResponseEntity<ApiResponse> updateFarmOrder(
      @RequestBody CancelOrderRequest cancelOrderRequest, @RequestParam boolean cancel) {
    if (!cancel) {}
    List<WaterDeliveryOrder> orders = new ArrayList<>();
    cancelOrderRequest
        .getDeliveryIds()
        .forEach(
            entry -> {
              var deliveryOrder =
                  deliveryService.getActiveOrder(cancelOrderRequest.getFarmId(), entry);
              deliveryService.cancelOrder(deliveryOrder);
              orders.add(deliveryOrder);
            });
    var cancelledOrders =
        orders.stream().map(ApiUtils::toDeliveryResponse).collect(Collectors.toUnmodifiableList());
    return ApiUtils.buildResponseEntity(cancelledOrders, HttpStatus.OK);
  }

    /**
     * Controller endpoint to fetch all orders related to a farmId
     * @param farmId
     * @return
     */
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

    /**
     * Controller endpoint to fetch an order related to a farmId
     * @param farmId
     * @return
     */
  @GetMapping(value = "farmers/{farmId}/orders/{orderId}")
  public ResponseEntity<ApiResponse> getFarmOrder(
      @PathVariable UUID farmId, @PathVariable UUID orderId) {
    return ApiUtils.buildResponseEntity(
        List.of(ApiUtils.toDeliveryResponse(deliveryService.getActiveOrder(farmId, orderId))),
        HttpStatus.OK);
  }
}
