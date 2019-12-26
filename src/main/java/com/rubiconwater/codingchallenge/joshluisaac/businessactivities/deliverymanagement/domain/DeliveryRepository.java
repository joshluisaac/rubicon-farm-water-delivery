package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityRepository;
import java.util.List;
import java.util.UUID;

public interface DeliveryRepository<T extends WaterDeliveryOrder> extends EntityRepository<T> {

  List<WaterDeliveryOrder> find(UUID farmId);
}
