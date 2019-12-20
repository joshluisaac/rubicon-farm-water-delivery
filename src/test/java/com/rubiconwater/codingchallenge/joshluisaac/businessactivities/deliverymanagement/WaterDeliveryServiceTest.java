package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WaterDeliveryServiceTest extends AbstractTest {

  @InjectMocks private WaterDeliveryService waterDeliveryService;

  @Mock private WaterDeliveryRepository waterDeliveryRepository;

  @Test
  public void whenFarmerPlacesRequest_ShouldAcceptNewRequestOrder() {
    // when a farmer submits a water order request
    LocalDateTime dateOrderReceived = LocalDateTime.of(2019, 9, 12, 13, 45, 11);
    LocalDateTime orderStartDate = LocalDateTime.of(2019, 10, 10, 6, 10, 11);
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    WaterDeliveryRequest requestOrder =
        createOrderRequest(farmId, dateOrderReceived, orderStartDate, 4);
    waterDeliveryService.acceptOrder(requestOrder);
    verify(waterDeliveryRepository, times(1)).save(requestOrder);
    assertThat(requestOrder.getDeliveryStatus())
        .isEqualByComparingTo(WaterDeliveryStatus.REQUESTED);
  }

  @Test
  public void shouldRejectOrderIfExists() {

  }
}
