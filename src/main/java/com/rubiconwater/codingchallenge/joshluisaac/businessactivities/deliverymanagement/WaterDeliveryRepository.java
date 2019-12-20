package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.DataStore;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.EntityRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WaterDeliveryRepository implements EntityRepository<WaterDeliveryRequest> {

  private final DataStore dataStore;

  @Autowired
  public WaterDeliveryRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public Optional<WaterDeliveryRequest> findById(UUID id) {
    return Optional.empty();
  }

  @Override
  public Collection<WaterDeliveryRequest> findAll() {
    return null;
  }

  @Override
  public void delete(WaterDeliveryRequest waterDeliveryRequest) {}

  @Override
  public void save(WaterDeliveryRequest waterDeliveryRequest) {}

  @Override
  public void update(WaterDeliveryRequest waterDeliveryRequest) {}

  @Override
  public void deleteById(UUID id) {}
}
