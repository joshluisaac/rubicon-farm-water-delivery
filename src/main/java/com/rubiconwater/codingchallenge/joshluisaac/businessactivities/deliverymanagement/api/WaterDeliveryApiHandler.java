package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryOrder;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class WaterDeliveryApiHandler {

  private final WaterDeliveryService deliveryService;
  private final ApiMapper apiMapper;

  @Autowired
  public WaterDeliveryApiHandler(WaterDeliveryService deliveryService, ApiMapper apiMapper) {
    this.deliveryService = deliveryService;
    this.apiMapper = apiMapper;
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
    var activeOrders =
        doPlaceOrder(acceptOrderRequest)
            .stream()
            .map(ApiMapper::toDeliveryResponse)
            .collect(Collectors.toUnmodifiableList());
    return ApiMapper.buildResponseEntity(activeOrders, HttpStatus.CREATED);
  }

  private List<WaterDeliveryOrder> doPlaceOrder(AcceptOrderRequest acceptOrderRequest) {
    List<WaterDeliveryOrder> orders = new ArrayList<>();
    acceptOrderRequest
        .getOrders()
        .forEach(
            entry -> {
              WaterDeliveryOrder deliveryOrder =
                  deliveryService.acceptOrder(apiMapper.toDeliveryOrder(acceptOrderRequest, entry));
              orders.add(deliveryOrder);
            });
    return orders;
  }

  /**
   * Controller endpoint to cancel an order.
   *
   * @param cancelOrderRequest
   * @param cancel
   * @return
   */
  @PutMapping(value = "farmers")
  public ResponseEntity<ApiResponse> cancelOrder(
      @Valid @RequestBody CancelOrderRequest cancelOrderRequest, @RequestParam boolean cancel) {
    if (!cancel) throw new IllegalArgumentException("The query parameter cancel must be 'true'");
    var cancelledOrders =
        doCancelOrder(cancelOrderRequest)
            .stream()
            .map(ApiMapper::toDeliveryResponse)
            .collect(Collectors.toUnmodifiableList());
    return ApiMapper.buildResponseEntity(cancelledOrders, HttpStatus.OK);
  }

  private List<WaterDeliveryOrder> doCancelOrder(CancelOrderRequest cancelOrderRequest) {
    List<WaterDeliveryOrder> orders = new ArrayList<>();
    cancelOrderRequest
        .getDeliveryIds()
        .forEach(
            entry -> {
              var deliveryOrder =
                  deliveryService.getDeliveryOrder(cancelOrderRequest.getFarmId(), entry);
              deliveryService.cancelOrder(deliveryOrder);
              orders.add(deliveryOrder);
            });
    return orders;
  }

  /**
   * Controller endpoint to fetch all orders related to a farmId
   *
   * @param farmId
   * @return
   */
  @GetMapping(value = "farmers/{farmId}")
  public ResponseEntity<ApiResponse> getAllFarmOrders(@PathVariable UUID farmId) {
    var farmOrders =
        deliveryService
            .getDeliveryOrders(farmId)
            .stream()
            .map(ApiMapper::toDeliveryResponse)
            .collect(Collectors.toUnmodifiableList());
    if (farmOrders.isEmpty()) throw new DeliveryOrderNotFoundException(farmId);
    return ApiMapper.buildResponseEntity(farmOrders, HttpStatus.OK);
  }

  /**
   * Controller endpoint to fetch an order related to a farmId
   *
   * @param farmId
   * @return
   */
  @GetMapping(value = "farmers/{farmId}/orders/{orderId}")
  public ResponseEntity<ApiResponse> getFarmOrder(
      @PathVariable UUID farmId, @PathVariable UUID orderId) {
    return ApiMapper.buildResponseEntity(
        List.of(ApiMapper.toDeliveryResponse(deliveryService.getDeliveryOrder(farmId, orderId))),
        HttpStatus.OK);
  }
}
