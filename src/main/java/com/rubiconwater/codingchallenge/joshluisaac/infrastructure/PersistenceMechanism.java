package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryOrder;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.FileUtils;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/** The responsibility of this class is to store delivery requests. */
@Service(value = "DataBaseService")
public class PersistenceMechanism {

  private static final Logger LOG = LoggerFactory.getLogger(PersistenceMechanism.class);

  @Value("file:data/DeliveryOrderDataSet.json")
  private Resource resource;

  // contains requests in all delivery states
  private Map<UUID, List<WaterDeliveryOrder>> cache = new HashMap<>();

  @PostConstruct
  void load() throws IOException {
    preconditions();
    InputStream inputStream = resource.getInputStream();
    cache =
        JsonMappers.buildReader()
            .forType(new TypeReference<Map<UUID, List<WaterDeliveryOrder>>>() {})
            .readValue(inputStream);

    LOG.info("Loaded and initialized dataset from  {}", resource.getFile().getAbsolutePath());
  }

  private void preconditions() throws IOException {
    URL url = resource.getURL();
    if (url == null) {
      throw new IllegalArgumentException("Dataset cannot be located in the path specified.");
    }
  }

  public Map<UUID, List<WaterDeliveryOrder>> getAll() {
    return cache;
  }

  public WaterDeliveryOrder add(WaterDeliveryOrder requestOrder) {
    boolean farmRecordExists = cache.containsKey(requestOrder.getFarmId());
    if (farmRecordExists) {
      cache.get(requestOrder.getFarmId()).add(requestOrder);
    } else {
      List<WaterDeliveryOrder> farmRequests = new ArrayList<>();
      farmRequests.add(requestOrder);
      cache.put(requestOrder.getFarmId(), farmRequests);
    }
    updateDatabase();
    return requestOrder;
  }

  public boolean delete(WaterDeliveryOrder requestOrder) {
    UUID requestId = requestOrder.getId();
    boolean result =
        cache.get(requestOrder.getFarmId()).removeIf(entry -> entry.getId().equals(requestId));
    updateDatabase();
    return result;
  }

  public boolean update(WaterDeliveryOrder updatedRequestOrder) {
    Optional<WaterDeliveryOrder> oldRequestOrder =
        cache
            .get(updatedRequestOrder.getFarmId())
            .stream()
            .filter(entry -> entry.getId().equals(updatedRequestOrder.getId()))
            .findFirst();
    if (oldRequestOrder.isPresent()) {
      oldRequestOrder.get().setDeliveryStatus(updatedRequestOrder.getDeliveryStatus());
      updateDatabase();
      return true;
    }
    return false;
  }

  /**
   * Return all active requests belonging to a farm
   *
   * @param farmId
   * @return
   */
  public List<WaterDeliveryOrder> findByFarmId(UUID farmId) {
    return cache.get(farmId);
  }

  private void doFlushToDisk() {
    try {
      String jsonValue =
          JsonMappers.buildWriter()
              .forType(new TypeReference<Map<UUID, List<WaterDeliveryOrder>>>() {})
              .writeValueAsString(cache);
      File file = resource.getFile();
      FileUtils.flushToDisk(jsonValue, file);
      LOG.info("Wrote updates to data store @ {}", file.getAbsolutePath());
    } catch (IOException ex) {
      // catching and re-throwing as runtime exception
      // because occurrence of this exception should abort the current unit of work
      throw new RuntimeException("Unable to write updates to dataset on disk.", ex);
    }
  }

  void setResource(Resource resource) {
    this.resource = resource;
  }

  public void updateDatabase() {
    doFlushToDisk();
  }
}
