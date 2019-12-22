package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.DataStore;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The responsibility of this class is to provide CRUD operations for storing delivery requests.
 * It's got a dependency on {@link DataStore} where the actual delivery entries are stored. It does
 * not decide what you do with the returned values. It's left to the caller to make that decision.
 */
@Repository
public class WaterDeliveryRepository implements DeliveryRepository<WaterDeliveryRequest> {

  private final DataStore dataStore;

  @Autowired
  public WaterDeliveryRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public List<WaterDeliveryRequest> find(UUID farmId) {
    return dataStore.findByFarmId(farmId);
  }

  @Override
  public Optional<WaterDeliveryRequest> find(UUID farmId, UUID requestOrderId) {
    return dataStore.find(farmId, requestOrderId);
  }

  @Override
  public void delete(WaterDeliveryRequest requestOrder) {
    dataStore.delete(requestOrder);
  }

  @Override
  public void save(WaterDeliveryRequest requestOrder) {
    dataStore.add(requestOrder);
  }

  @Override
  public void update(WaterDeliveryRequest requestOrder) {
    dataStore.update(requestOrder);
  }

  @Override
  public void deleteById(UUID id) {}
}
