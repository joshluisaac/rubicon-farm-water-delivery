package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.DeliveryOrderNotFoundException;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.Errors;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityService;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The responsibility of this class is to process delivery requests. */
@Service
public class WaterDeliveryService implements EntityService<WaterDeliveryOrder> {

  private final DeliveryRepository<WaterDeliveryOrder> repository;

  @Autowired
  public WaterDeliveryService(DeliveryRepository<WaterDeliveryOrder> repository) {
    this.repository = repository;
  }

  /**
   * Accepts an order if it meets certain conditions.
   *
   * @param requestOrder
   */
  public WaterDeliveryOrder acceptOrder(WaterDeliveryOrder requestOrder) {
    checkExitingOrder(requestOrder);
    checkTimeFrameCollision(requestOrder);
    requestOrder.setDeliveryStatus(WaterDeliveryStatus.REQUESTED);
    return repository.save(requestOrder);
  }

  /**
   * Cancels an order if it is in any of {@link WaterDeliveryStatus#IN_PROGRESS} or {@link
   * WaterDeliveryStatus#REQUESTED} states
   *
   * @param requestOrder
   */
  public WaterDeliveryOrder cancelOrder(WaterDeliveryOrder requestOrder) {
    if (requestOrder.getDeliveryStatus().isAllowCancel()) {
      requestOrder.setDeliveryStatus(WaterDeliveryStatus.CANCELLED);
      repository.update(requestOrder);
      return requestOrder;
    }
    throw new IllegalArgumentException(
        String.format(Errors.CANCEL_ORDER_NOT_ALLOWED.getDescription(), requestOrder.getId()));
  }

  public List<WaterDeliveryOrder> getDeliveryOrders(UUID farmId) {
    var result = repository.find(farmId);
    if (result != null) {
      return result;
    }
    throw new IllegalArgumentException(
        String.format(Errors.FARM_ID_NOT_FOUND.getDescription(), farmId));
  }

  // JUN: Change IllegalArgumentException -> DeliveryOrderNotFoundException
  public WaterDeliveryOrder getDeliveryOrder(UUID farmId, UUID requestOrderId) {
    var result = getDeliveryOrders(farmId);
    return result
        .stream()
        .filter(entry -> entry.getId().equals(requestOrderId))
        .findFirst()
        .orElseThrow(() -> new DeliveryOrderNotFoundException(requestOrderId));
  }

  private void checkExitingOrder(WaterDeliveryOrder requestOrder) {
    boolean result =
        checkStream(
            requestOrder,
            entry ->
                (entry.getHash().equals(requestOrder.getHash()))
                    && (entry.getDeliveryStatus() != WaterDeliveryStatus.CANCELLED));
    if (result)
      throw new IllegalArgumentException(
          String.format(
              Errors.EXISTING_ORDER_DUPLICATION.getDescription(),
              requestOrder.getOrderStartDate(),
              requestOrder.getSupplyDuration()));
  }

  private void checkTimeFrameCollision(WaterDeliveryOrder requestOrder) {
    boolean result =
        checkStream(
            requestOrder,
            entry ->
                (entry.getTimeFrame().isBetweenTimeFrameOf(requestOrder.getTimeFrame()))
                    && (entry.getDeliveryStatus() != WaterDeliveryStatus.CANCELLED));
    if (result) throw new IllegalArgumentException(Errors.TIME_FRAME_COLLISION.getDescription());
  }

  private boolean checkStream(WaterDeliveryOrder requestOrder, Predicate<WaterDeliveryOrder> pred) {
    return requestOrderStream(requestOrder).anyMatch(pred);
  }

  private Stream<WaterDeliveryOrder> requestOrderStream(WaterDeliveryOrder requestOrder) {
    var requestOrders = repository.find(requestOrder.getFarmId());
    List<WaterDeliveryOrder> result =
        (requestOrders != null) ? requestOrders : Collections.emptyList();
    return result.stream();
  }
}
