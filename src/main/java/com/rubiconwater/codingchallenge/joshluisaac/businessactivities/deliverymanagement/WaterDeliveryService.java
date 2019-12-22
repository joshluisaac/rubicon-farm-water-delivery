package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
  public void acceptOrder(WaterDeliveryOrder requestOrder) {
    checkExitingOrder(requestOrder);
    checkTimeFrameCollision(requestOrder);
    requestOrder.setDeliveryStatus(WaterDeliveryStatus.REQUESTED);
    repository.save(requestOrder);
  }

  /**
   * Cancels an order if it is in any of {@link WaterDeliveryStatus#IN_PROGRESS} or {@link
   * WaterDeliveryStatus#REQUESTED} states
   *
   * @param requestOrderId
   */
  public void cancelOrder(UUID farmId, UUID requestOrderId) {
    Optional<WaterDeliveryOrder> maybeRequestOrder = repository.find(farmId, requestOrderId);
    WaterDeliveryOrder requestOrder =
        maybeRequestOrder.orElseThrow(
            () ->
                new IllegalArgumentException(String.format("%s does not exists", requestOrderId)));
    if (requestOrder.getDeliveryStatus().isAllowCancel()) {
      requestOrder.setDeliveryStatus(WaterDeliveryStatus.CANCELLED);
      repository.update(requestOrder);
      return;
    }
    throw new IllegalArgumentException(
        String.format(
            "Cancel order operation not allowed. %n Please check order delivery status for orderId (%s)",
            requestOrderId));
  }

  public List<WaterDeliveryOrder> getActiveOrders(UUID farmId) {
    var result = repository.find(farmId);
    if (result != null) {
      return result;
    }
    return Collections.emptyList();
  }

  public WaterDeliveryOrder getActiveOrder(UUID farmId, UUID requestOrderId) {
    return repository.find(farmId, requestOrderId).orElseThrow();
  }

  private void checkExitingOrder(WaterDeliveryOrder requestOrder) {
    boolean result =
        checkStream(requestOrder, entry -> entry.getHash().equals(requestOrder.getHash()));
    if (result) throw new IllegalArgumentException("The requested order exists.");
  }

  private void checkTimeFrameCollision(WaterDeliveryOrder requestOrder) {
    boolean result =
        checkStream(
            requestOrder,
            entry -> entry.getTimeFrame().isBetweenTimeFrameOf(requestOrder.getTimeFrame()));
    if (result)
      throw new IllegalArgumentException(
          "The requested order falls within the time frame of another order");
  }

  private boolean checkStream(WaterDeliveryOrder requestOrder, Predicate<WaterDeliveryOrder> pred) {
    return activeOrderStream(requestOrder).anyMatch(pred);
  }

  private Stream<WaterDeliveryOrder> activeOrderStream(WaterDeliveryOrder requestOrder) {
    return getActiveOrders(requestOrder.getFarmId()).stream();
  }
}
