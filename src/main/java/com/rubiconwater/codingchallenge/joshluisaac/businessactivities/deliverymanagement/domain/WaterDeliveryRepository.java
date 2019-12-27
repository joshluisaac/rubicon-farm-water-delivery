package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.PersistenceMechanism;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The responsibility of this class is to provide CRUD operations for storing delivery requests.
 * It's got a dependency on {@link PersistenceMechanism} where the actual delivery entries are
 * stored. It does not decide what you do with the returned values. It's left to the caller to make
 * that decision.
 */
@Repository
public class WaterDeliveryRepository implements DeliveryRepository<WaterDeliveryOrder> {

  private final PersistenceMechanism persistenceMechanism;

  @Autowired
  public WaterDeliveryRepository(PersistenceMechanism persistenceMechanism) {
    this.persistenceMechanism = persistenceMechanism;
  }

  @Override
  public List<WaterDeliveryOrder> find(UUID farmId) {
    return persistenceMechanism.findByFarmId(farmId);
  }

  @Override
  public void delete(WaterDeliveryOrder requestOrder) {
    persistenceMechanism.delete(requestOrder);
  }

  @Override
  public WaterDeliveryOrder save(WaterDeliveryOrder requestOrder) {
    return persistenceMechanism.add(requestOrder);
  }

  @Override
  public void update(WaterDeliveryOrder requestOrder) {
    persistenceMechanism.update(requestOrder);
  }
}
