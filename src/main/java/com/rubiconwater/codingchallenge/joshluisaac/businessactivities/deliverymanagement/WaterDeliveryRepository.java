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
  public Optional<WaterDeliveryRequest> findById(UUID requestOrderId) {
    return Optional.empty();
  }

  @Override
  public Collection<WaterDeliveryRequest> findAll() {
    return null;
  }

  @Override
  public boolean isExisting(WaterDeliveryRequest requestOrder) {
    UUID farmId = requestOrder.getFarmId();
    String hash = requestOrder.getHash();
    // dataStore.
    return false;
  }

  @Override
  public List<WaterDeliveryRequest> findByFarmId(UUID farmId) {
    return Collections.emptyList();
  }

  @Override
  public void delete(WaterDeliveryRequest requestOrder) {}

  @Override
  public void save(WaterDeliveryRequest requestOrder) {}

  @Override
  public void update(WaterDeliveryRequest requestOrder) {}

  @Override
  public void deleteById(UUID id) {}
}
