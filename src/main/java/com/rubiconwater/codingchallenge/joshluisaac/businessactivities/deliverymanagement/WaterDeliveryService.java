package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The responsibility of this class is to process delivery requests. */
@Service
public class WaterDeliveryService implements EntityService<WaterDeliveryRequest> {

  private final DeliveryRepository<WaterDeliveryRequest> waterDeliveryRepository;

  @Autowired
  public WaterDeliveryService(DeliveryRepository<WaterDeliveryRequest> waterDeliveryRepository) {
    this.waterDeliveryRepository = waterDeliveryRepository;
  }

  public void acceptOrder(WaterDeliveryRequest requestOrder) {
    checkExitingOrder(requestOrder);
    requestOrder.setDeliveryStatus(WaterDeliveryStatus.REQUESTED);
    waterDeliveryRepository.save(requestOrder);
  }

  /**
   * Cancels an order if it is in any of {@link WaterDeliveryStatus#IN_PROGRESS} or {@link
   * WaterDeliveryStatus#REQUESTED} states
   *
   * @param requestOrderId
   */
  public void cancelOrder(UUID requestOrderId) {
    Optional<WaterDeliveryRequest> maybeRequestOrder =
        waterDeliveryRepository.findById(requestOrderId);
    WaterDeliveryRequest requestOrder =
        maybeRequestOrder.orElseThrow(
            () ->
                new IllegalArgumentException(String.format("%s does not exists", requestOrderId)));
    if (requestOrder.getDeliveryStatus().isAllowCancel()) {
      requestOrder.setDeliveryStatus(WaterDeliveryStatus.CANCELLED);
      waterDeliveryRepository.update(requestOrder);
      return;
    }
    throw new IllegalArgumentException(
        String.format(
            "Cancel order operation not allowed. %n Please check order delivery status for orderId (%s)",
            requestOrderId));
  }

  public WaterDeliveryStatus getOrderStatus(UUID requestOrderId) {
    Optional<WaterDeliveryRequest> maybeRequest = waterDeliveryRepository.findById(requestOrderId);
    WaterDeliveryRequest requestOrder =
        maybeRequest.orElseThrow(
            () ->
                new IllegalArgumentException(String.format("%s does not exists", requestOrderId)));
    return requestOrder.getDeliveryStatus();
  }

  private void rejectOperation() {}

  private void checkExitingOrder(WaterDeliveryRequest requestOrder) {
    if (waterDeliveryRepository.isExisting(requestOrder)) {
      throw new IllegalArgumentException("The requested order exists.");
    }
    waterDeliveryRepository
        .findByFarmId(requestOrder.getFarmId())
        .stream()
        .filter(entry -> entry.isBetweenTimeFrameOf(requestOrder))
        .findAny()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "The requested order falls within the time frame of another order"));
  }
}
