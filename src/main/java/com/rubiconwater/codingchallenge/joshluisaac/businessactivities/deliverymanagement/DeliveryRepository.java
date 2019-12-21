package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityRepository;
import java.util.List;
import java.util.UUID;

public interface DeliveryRepository<T extends WaterDeliveryRequest> extends EntityRepository<T> {

  boolean isExisting(WaterDeliveryRequest requestOrder);

  List<WaterDeliveryRequest> findByFarmId(UUID farmId);
}
