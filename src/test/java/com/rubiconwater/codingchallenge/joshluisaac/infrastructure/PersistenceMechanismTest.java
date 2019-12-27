package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryOrder;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryStatus;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
public class PersistenceMechanismTest implements AbstractTest {

  @InjectMocks PersistenceMechanism persistenceMechanism;

  @Mock Resource resource;

  @BeforeEach
  public void beforeDataStoreTest() throws IOException {
    URL url =
        Thread.currentThread().getContextClassLoader().getResource("TestDeliveryOrderDataSet.json");
    InputStream inputStream =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("TestDeliveryOrderDataSet.json");
    when(resource.getInputStream()).thenReturn(inputStream);
    when(resource.getURL()).thenReturn(url);
    when(resource.getFile()).thenReturn(new File("TestDeliveryOrderDataSet.json"));
    persistenceMechanism.setResource(resource);
    persistenceMechanism.load();
  }

  @Test
  void testShould_AddDeliveryOrderToCache() {
    var requestOrder = setupFakeDeliveryOrder();
    persistenceMechanism.add(requestOrder);
    assertThat(persistenceMechanism.findByFarmId(requestOrder.getFarmId()).size()).isEqualTo(1);
  }

  @Test
  void testShould_DeleteWaterDeliveryOrder() {
    var requestOrder = setupFakeDeliveryOrder();
    persistenceMechanism.add(requestOrder);
    boolean result = persistenceMechanism.delete(requestOrder);
    assertThat(result).isTrue();
  }

  @Test
  void testShould_UpdateWaterDeliveryOrder() {
    var requestOrder = setupFakeDeliveryOrder();
    persistenceMechanism.add(requestOrder);
    requestOrder.setDeliveryStatus(WaterDeliveryStatus.CANCELLED);
    boolean result = persistenceMechanism.update(requestOrder);
    assertThat(result).isTrue();
  }

  @Test
  void testShould_SKipUpdate_WhenOrderNotFound() {
    var delivery =
        WaterDeliveryOrder.builder()
            .dateReceived(LocalDateTime.parse("2019-09-12T13:45:11"))
            .orderStartDate(LocalDateTime.parse("2019-10-10T06:10:11"))
            .farmId(UuidUtils.toUuid("cc6b1ab4-249d-11ea-978f-2e728ce88125"))
            .supplyDuration(8)
            .build();
    persistenceMechanism.add(delivery);
    boolean result =
        persistenceMechanism.update(
            WaterDeliveryOrder.builder()
                .dateReceived(LocalDateTime.parse("2019-09-12T13:45:11"))
                .orderStartDate(LocalDateTime.parse("2019-10-10T06:10:11"))
                .farmId(UuidUtils.toUuid("cc6b1ab4-249d-11ea-978f-2e728ce88125"))
                .supplyDuration(8)
                .build());
    assertThat(result).isFalse();
  }

  @Test
  void testShouldReturn_AllByFarmId() {
    var requestOrder = setupFakeDeliveryOrder();
    persistenceMechanism.add(requestOrder);
    var requestOrder2 = setupFakeDeliveryOrder();
    persistenceMechanism.add(requestOrder2);
    assertThat(persistenceMechanism.findByFarmId(requestOrder.getFarmId()).size()).isEqualTo(2);
  }

  @Test
  public void throwIllegalArgumentException_OnURL_NotFound() throws IOException {
    when(resource.getURL()).thenReturn(null);
    Throwable thrown = catchThrowable(() -> persistenceMechanism.load());
    assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
  }

  private WaterDeliveryOrder setupFakeDeliveryOrder() {
    LocalDateTime dateOrderReceived = LocalDateTime.parse("2019-09-12T13:45:11");
    LocalDateTime orderStartDate = LocalDateTime.parse("2019-10-10T06:10:11");
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    return createOrderRequest(farmId, dateOrderReceived, orderStartDate, 4);
  }
}
