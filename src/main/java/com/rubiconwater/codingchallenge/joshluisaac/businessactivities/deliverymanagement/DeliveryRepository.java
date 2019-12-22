package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository<T extends WaterDeliveryOrder> extends EntityRepository<T> {

  List<WaterDeliveryOrder> find(UUID farmId);

  Optional<WaterDeliveryOrder> find(UUID farmId, UUID requestOrderId);
}
