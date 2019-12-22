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
public class WaterDeliveryService implements EntityService<WaterDeliveryRequest> {

  private final DeliveryRepository<WaterDeliveryRequest> repository;

  @Autowired
  public WaterDeliveryService(DeliveryRepository<WaterDeliveryRequest> repository) {
    this.repository = repository;
  }

  /**
   * Accepts an order if it meets certain conditions.
   *
   * @param requestOrder
   */
  public void acceptOrder(WaterDeliveryRequest requestOrder) {
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
    Optional<WaterDeliveryRequest> maybeRequestOrder = repository.find(farmId, requestOrderId);
    WaterDeliveryRequest requestOrder =
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

  public List<WaterDeliveryRequest> getActiveOrders(UUID farmId) {
    var result = repository.find(farmId);
    if (result != null) {
      return result;
    }
    return Collections.emptyList();
  }

  public WaterDeliveryRequest getActiveOrder(UUID farmId, UUID requestOrderId) {
    return repository.find(farmId, requestOrderId).orElseThrow();
  }

  private void checkExitingOrder(WaterDeliveryRequest requestOrder) {
    boolean result =
        checkStream(requestOrder, entry -> entry.getHash().equals(requestOrder.getHash()));
    if (result) throw new IllegalArgumentException("The requested order exists.");
  }

  private void checkTimeFrameCollision(WaterDeliveryRequest requestOrder) {
    boolean result =
        checkStream(
            requestOrder,
            entry -> entry.getTimeFrame().isBetweenTimeFrameOf(requestOrder.getTimeFrame()));
    if (result)
      throw new IllegalArgumentException(
          "The requested order falls within the time frame of another order");
  }

  private boolean checkStream(
      WaterDeliveryRequest requestOrder, Predicate<WaterDeliveryRequest> pred) {
    return activeOrderStream(requestOrder).anyMatch(pred);
  }

  private Stream<WaterDeliveryRequest> activeOrderStream(WaterDeliveryRequest requestOrder) {
    return getActiveOrders(requestOrder.getFarmId()).stream();
  }
}
