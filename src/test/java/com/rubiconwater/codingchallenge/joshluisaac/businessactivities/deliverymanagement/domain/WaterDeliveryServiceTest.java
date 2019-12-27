package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.DeliveryOrderNotFoundException;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.Errors;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WaterDeliveryServiceTest implements AbstractTest {

  @InjectMocks private WaterDeliveryService waterDeliveryService;

  @Mock private WaterDeliveryRepository waterDeliveryRepository;

  @Test
  public void whenFarmerPlaceRequest_ShouldAcceptNewRequestOrder() {
    var requestOrder = setupFakeDeliveryOrder();
    waterDeliveryService.acceptOrder(requestOrder);
    verify(waterDeliveryRepository, times(1)).save(requestOrder);
    assertThat(requestOrder.getDeliveryStatus())
        .isEqualByComparingTo(WaterDeliveryStatus.REQUESTED);
  }

  @Test
  void shouldSetOrderToCancelled_WhenAnOrderIsCancelled() {
    var requestOrder = setupFakeDeliveryOrder();
    waterDeliveryService.cancelOrder(requestOrder);
    verify(waterDeliveryRepository, times(1)).update(requestOrder);
    assertThat(requestOrder.getDeliveryStatus())
        .isEqualByComparingTo(WaterDeliveryStatus.CANCELLED);
  }

  // JUN DOCS: You cannot cancel what has already been delivered or previously cancelled
  @Test
  void shouldRejectCancelOrder_WhenOrderDeliveryStatusIsDelivered() {
    var requestOrder = setupFakeDeliveryOrder();
    requestOrder.setDeliveryStatus(WaterDeliveryStatus.DELIVERED);
    Throwable throwable = catchThrowable(() -> waterDeliveryService.cancelOrder(requestOrder));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(
            String.format(Errors.CANCEL_ORDER_NOT_ALLOWED.getDescription(), requestOrder.getId()));
  }

  @Test
  void shouldRejectCancelOrder_WhenOrderDeliveryStatusIsCancelled() {
    var requestOrder = setupFakeDeliveryOrder();
    requestOrder.setDeliveryStatus(WaterDeliveryStatus.CANCELLED);
    Throwable throwable = catchThrowable(() -> waterDeliveryService.cancelOrder(requestOrder));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(
            String.format(Errors.CANCEL_ORDER_NOT_ALLOWED.getDescription(), requestOrder.getId()));
  }

  @Test
  void shouldReturn_EmptyCollection_WhenOrdersNotFound() {
    var requestOrder = setupFakeDeliveryOrder();
    when(waterDeliveryRepository.find(requestOrder.getFarmId())).thenReturn(null);
    Throwable throwable =
        catchThrowable(() -> waterDeliveryService.getDeliveryOrders(requestOrder.getFarmId()));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(
            String.format(
                Errors.FARM_ID_NOT_FOUND.getDescription(), "1ddeab59-8bb1-4292-8fe4-7a6769411fe5"));
  }

  @Test
  void shouldReturn_ListOfOrders() {
    var requestOrder = setupFakeDeliveryOrder();
    when(waterDeliveryRepository.find(requestOrder.getFarmId())).thenReturn(List.of(requestOrder));
    assertThat(waterDeliveryService.getDeliveryOrders(requestOrder.getFarmId()).size())
        .isEqualTo(1);
  }

  @Test
  void shouldReturn_DeliveryOrder() {
    var requestOrder = setupFakeDeliveryOrder();
    when(waterDeliveryRepository.find(requestOrder.getFarmId())).thenReturn(List.of(requestOrder));
    assertThat(
            waterDeliveryService
                .getDeliveryOrder(requestOrder.getFarmId(), requestOrder.getId())
                .getHash())
        .isEqualTo(requestOrder.getHash());
  }

  @Test
  void shouldThrowException_WhenDeliveryOrderIsEmpty() {
    var requestOrder = setupFakeDeliveryOrder();
    when(waterDeliveryRepository.find(requestOrder.getFarmId())).thenReturn(List.of(requestOrder));
    Supplier<WaterDeliveryOrder> deliveryOrderSupplier =
        () -> waterDeliveryService.getDeliveryOrder(requestOrder.getFarmId(), null);
    Throwable throwable = catchThrowable(deliveryOrderSupplier::get);
    assertThat(throwable).isInstanceOf(DeliveryOrderNotFoundException.class)
    // .hasMessageStartingWith(Errors.ORDER_NOT_FOUND.getDescription())
    ;
  }

  @Test
  void shouldRejectAcceptOrder_WhenOrderAlreadyExists() {
    var requestOrder = setupFakeDeliveryOrder();
    when(waterDeliveryRepository.find(requestOrder.getFarmId())).thenReturn(List.of(requestOrder));
    Throwable throwable = catchThrowable(() -> waterDeliveryService.acceptOrder(requestOrder));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(
            String.format(
                Errors.EXISTING_ORDER_DUPLICATION.getDescription(),
                requestOrder.getOrderStartDate(),
                requestOrder.getSupplyDuration()));
  }

  @Test
  void shouldRejectAcceptOrder_WhenOrderOverlaps() {
    var requestOrder = setupFakeDeliveryOrder();
    when(waterDeliveryRepository.find(requestOrder.getFarmId()))
        .thenReturn(List.of(setupOverlappingFakeDeliveryOrder()));
    Throwable throwable = catchThrowable(() -> waterDeliveryService.acceptOrder(requestOrder));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(Errors.TIME_FRAME_COLLISION.getDescription());
  }

  private WaterDeliveryOrder setupFakeDeliveryOrder() {
    LocalDateTime dateOrderReceived = LocalDateTime.parse("2019-09-12T13:45:11");
    LocalDateTime orderStartDate = LocalDateTime.parse("2019-10-10T06:10:11");
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    return createOrderRequest(farmId, dateOrderReceived, orderStartDate, 4);
  }

  private WaterDeliveryOrder setupOverlappingFakeDeliveryOrder() {
    LocalDateTime dateOrderReceived = LocalDateTime.parse("2019-09-12T13:45:11");
    LocalDateTime orderStartDate = LocalDateTime.parse("2019-10-10T06:10:11");
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    return createOrderRequest(farmId, dateOrderReceived, orderStartDate, 2);
  }
}
