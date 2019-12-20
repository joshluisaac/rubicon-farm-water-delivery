package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityRepository;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterDeliveryService implements EntityService<WaterDeliveryRequest> {

  private final EntityRepository<WaterDeliveryRequest> waterDeliveryRepository;

  @Autowired
  public WaterDeliveryService(EntityRepository<WaterDeliveryRequest> waterDeliveryRepository) {
    this.waterDeliveryRepository = waterDeliveryRepository;
  }

  public void acceptOrder(WaterDeliveryRequest request) {
    request.setDeliveryStatus(WaterDeliveryStatus.REQUESTED);
    waterDeliveryRepository.save(request);
  }

  /**
   * Cancels an order if it is in any of {@link WaterDeliveryStatus#IN_PROGRESS} or {@link
   * WaterDeliveryStatus#REQUESTED} states
   *
   * @param requestOrderId
   */
  public void cancelOrder(UUID requestOrderId) {
    Optional<WaterDeliveryRequest> maybeRequest = waterDeliveryRepository.findById(requestOrderId);
    WaterDeliveryRequest requestOrder =
        maybeRequest.orElseThrow(
            () -> new IllegalArgumentException(String.format("%s does not exists", requestOrderId)));
    //if (requestOrder.getDeliveryStatus())
    requestOrder.setDeliveryStatus(WaterDeliveryStatus.CANCELLED);
    waterDeliveryRepository.update(requestOrder);
  }

  public WaterDeliveryStatus getOrderStatus(UUID requestOrderId){
    Optional<WaterDeliveryRequest> maybeRequest = waterDeliveryRepository.findById(requestOrderId);
    WaterDeliveryRequest requestOrder =
            maybeRequest.orElseThrow(
                    () -> new IllegalArgumentException(String.format("%s does not exists", requestOrderId)));
    return requestOrder.getDeliveryStatus();
  }

  private void rejectOperation() {}
}
