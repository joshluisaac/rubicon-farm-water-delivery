package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.PersistenceMechanism;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BatchDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DeliveryStatusBatchTest implements AbstractTest {

  @Mock private PersistenceMechanism persistenceMechanism;
  @Mock private BatchDate batchDate;
  @InjectMocks DeliveryStatusBatch deliveryStatusBatch;


  @Test
  void shouldCancelRequestedOrder_WhenItIsPastDeliveryEndDate() {
      String evaluationDate = "2018-10-10T09:09:09";
    UUID farmIdA = UuidUtils.create();
    WaterDeliveryOrder aRequestOrder1 =
        createOrderRequest(
            farmIdA,
            LocalDateTime.parse("2017-10-10T09:09:09"),
            LocalDateTime.parse("2017-10-10T09:09:09"),
            2);
    WaterDeliveryOrder aRequestOrder2 =
        createOrderRequest(
            farmIdA,
            LocalDateTime.parse("2017-10-11T09:09:09"),
            LocalDateTime.parse("2017-10-11T09:09:09"),
            2);
    WaterDeliveryOrder aRequestOrder3 =
        createOrderRequest(
            farmIdA,
            LocalDateTime.parse("2017-10-12T09:09:09"),
            LocalDateTime.parse("2017-10-12T09:09:09"),
            2);
    WaterDeliveryOrder aRequestOrder4 =
        createOrderRequest(
            farmIdA,
            LocalDateTime.parse("2017-10-13T09:09:09"),
            LocalDateTime.parse("2017-10-13T09:09:09"),
            2);
    aRequestOrder3.setDeliveryStatus(WaterDeliveryStatus.CANCELLED);
    aRequestOrder4.setDeliveryStatus(WaterDeliveryStatus.DELIVERED);

    LocalDateTime processingDate = LocalDateTime.parse(evaluationDate);
    when(batchDate.getBatchDate()).thenReturn(processingDate);
    when(persistenceMechanism.getAll())
        .thenReturn(
            Map.of(
                farmIdA, List.of(aRequestOrder1, aRequestOrder2, aRequestOrder3, aRequestOrder4)));
    deliveryStatusBatch.invoke();
    assertThat(aRequestOrder2.getDeliveryStatus())
        .isEqualByComparingTo(WaterDeliveryStatus.CANCELLED);
  }

    @Test
    void shouldChangeRequested_To_InProgress_WhenWithinDeliveryWindow() {
        String evaluationDate = "2018-10-10T09:09:09";
        UUID farmIdA = UuidUtils.create();
        WaterDeliveryOrder aRequestOrder1 =
                createOrderRequest(
                        farmIdA,
                        LocalDateTime.parse("2017-10-10T09:09:09"),
                        LocalDateTime.parse("2018-10-07T09:09:09"),
                        200);
        WaterDeliveryOrder aRequestOrder2 =
                createOrderRequest(
                        farmIdA,
                        LocalDateTime.parse("2017-10-11T09:09:09"),
                        LocalDateTime.parse("2017-10-11T09:09:09"),
                        2);
        LocalDateTime processingDate = LocalDateTime.parse(evaluationDate);
        when(batchDate.getBatchDate()).thenReturn(processingDate);
        when(persistenceMechanism.getAll())
                .thenReturn(
                        Map.of(
                                farmIdA, List.of(aRequestOrder1, aRequestOrder2)));
        deliveryStatusBatch.invoke();
        assertThat(aRequestOrder1.getDeliveryStatus())
                .isEqualByComparingTo(WaterDeliveryStatus.IN_PROGRESS);
    }


    @Test
    void shouldNotChangeStatusFor_OrdersAfterBatchDate() {
        String evaluationDate = "2018-10-10T09:09:09";
        UUID farmIdA = UuidUtils.create();
        WaterDeliveryOrder aRequestOrder1 =
                createOrderRequest(
                        farmIdA,
                        LocalDateTime.parse("2017-10-10T09:09:09"),
                        LocalDateTime.parse("2018-10-11T09:09:09"),
                        200);
        LocalDateTime processingDate = LocalDateTime.parse(evaluationDate);
        when(batchDate.getBatchDate()).thenReturn(processingDate);
        when(persistenceMechanism.getAll())
                .thenReturn(
                        Map.of(
                                farmIdA, List.of(aRequestOrder1)));
        deliveryStatusBatch.invoke();
        assertThat(aRequestOrder1.getDeliveryStatus())
                .isEqualByComparingTo(WaterDeliveryStatus.REQUESTED);
    }


    /**
     * When an order is in-progress and past it's delivery end date,
     * It is marked as delivered when next the batch is executed
     */
  @Test
  void markInProgressToDelivered_WhenOrderIsPastDeliveryEndDate() {
      String evaluationDate = "2018-10-13T09:09:09";
      UUID farmIdA = UuidUtils.create();
      WaterDeliveryOrder aRequestOrder1 =
              createOrderRequest(
                      farmIdA,
                      LocalDateTime.parse("2017-10-10T09:09:09"),
                      LocalDateTime.parse("2018-10-11T09:09:09"),
                      3);
      LocalDateTime processingDate = LocalDateTime.parse(evaluationDate);
      when(batchDate.getBatchDate()).thenReturn(processingDate);
      when(persistenceMechanism.getAll())
              .thenReturn(
                      Map.of(
                              farmIdA, List.of(aRequestOrder1)));
      aRequestOrder1.setDeliveryStatus(WaterDeliveryStatus.IN_PROGRESS);
      deliveryStatusBatch.invoke();
      assertThat(aRequestOrder1.getDeliveryStatus())
              .isEqualByComparingTo(WaterDeliveryStatus.DELIVERED);


  }


}
