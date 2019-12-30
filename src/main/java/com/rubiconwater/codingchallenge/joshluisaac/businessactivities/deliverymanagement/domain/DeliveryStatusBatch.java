package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.PersistenceMechanism;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BatchDate;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Order delivery status batch
 */
@Slf4j
@Component
public class DeliveryStatusBatch {

  private PersistenceMechanism persistenceMechanism;
  private BatchDate batchDate;

  @Autowired
  public DeliveryStatusBatch(PersistenceMechanism persistenceMechanism,BatchDate batchDate) {
    this.persistenceMechanism = persistenceMechanism;
    this.batchDate = batchDate;
  }


  @Scheduled(fixedRate = 120000)
  public void invoke() {
    log.info(
        "Processing delivery status batch for {}", WaterDeliveryUtils.toIsoLocalDateTime(batchDate.getBatchDate()));

    processEntries(
        this::isRequestedAndPastDeliveryDate,
        entry -> entry.setDeliveryStatus(WaterDeliveryStatus.CANCELLED));

      processEntries(
              this::isRequestedAndWithInDeliveryWindow,
              entry -> entry.setDeliveryStatus(WaterDeliveryStatus.IN_PROGRESS));

    processEntries(
            this::isInProgressAndPastDeliveryDate,
            entry -> entry.setDeliveryStatus(WaterDeliveryStatus.DELIVERED));

    persistenceMechanism.updateDatabase();
    batchDate.setBatchDate(LocalDateTime.now());
  }

  private void processEntries(
      Predicate<WaterDeliveryOrder> pred, Consumer<WaterDeliveryOrder> action) {
    persistenceMechanism
        .getAll()
        .forEach((key, value) -> value.stream().filter(pred).forEach(e -> {

          action.accept(e);
          log.info("Updated requestId {} of farmId {} to {}", e.getId(), e.getFarmId(), e.getDeliveryStatus());
        }));
  }

  private boolean isRequestedAndPastDeliveryDate(WaterDeliveryOrder requestOrder) {
    return WaterDeliveryUtils.isRequested(requestOrder)
        && WaterDeliveryUtils.isPastDeliveryDueDate(requestOrder, batchDate.getBatchDate());
  }

  private boolean isRequestedAndWithInDeliveryWindow(WaterDeliveryOrder requestOrder) {
    return WaterDeliveryUtils.isRequested(requestOrder)
        && (requestOrder.getTimeFrame().getStartDate().isBefore(batchDate.getBatchDate())
            && requestOrder.getTimeFrame().getEndDate().isAfter(batchDate.getBatchDate()));
  }

  private boolean isInProgressAndPastDeliveryDate(WaterDeliveryOrder requestOrder) {
    return WaterDeliveryUtils.isInProgress(requestOrder)
            && WaterDeliveryUtils.isPastDeliveryDueDate(requestOrder, batchDate.getBatchDate());
  }




}
