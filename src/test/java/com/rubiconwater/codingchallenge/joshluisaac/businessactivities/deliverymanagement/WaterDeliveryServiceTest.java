package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.time.LocalDateTime;
import java.util.UUID;
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
    // when a farmer submits a water order request
    LocalDateTime dateOrderReceived = LocalDateTime.of(2019, 9, 12, 13, 45, 11);
    LocalDateTime orderStartDate = LocalDateTime.of(2019, 10, 10, 6, 10, 11);
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    var requestOrder = createOrderRequest(farmId, dateOrderReceived, orderStartDate, 4);
    when(waterDeliveryRepository.isExisting(requestOrder)).thenReturn(false);
    waterDeliveryService.acceptOrder(requestOrder);
    verify(waterDeliveryRepository, times(1)).save(requestOrder);
    assertThat(requestOrder.getDeliveryStatus())
        .isEqualByComparingTo(WaterDeliveryStatus.REQUESTED);

    // assertThat(LocalDateTime.now()).isBeforeOrEqualTo((LocalDateTime.now())
  }

  @Test
  // two request orders are equal if they are logically equal.
  // logical equality uses the combination of farmId,order start and end dates.
  void shouldRejectOrderIfExists() {
    LocalDateTime dateOrderReceived = LocalDateTime.of(2019, 9, 12, 13, 45, 11);
    LocalDateTime orderStartDate = LocalDateTime.of(2019, 10, 10, 6, 10, 11);
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    var requestOrder = createOrderRequest(farmId, dateOrderReceived, orderStartDate, 4);
    when(waterDeliveryRepository.isExisting(requestOrder)).thenReturn(true);
    Throwable throwable = catchThrowable(() -> waterDeliveryService.acceptOrder(requestOrder));
    assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
  }
}
