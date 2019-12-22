package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository<T extends WaterDeliveryRequest> extends EntityRepository<T> {

  List<WaterDeliveryRequest> find(UUID farmId);

  Optional<WaterDeliveryRequest> find(UUID farmId, UUID requestOrderId);
}
