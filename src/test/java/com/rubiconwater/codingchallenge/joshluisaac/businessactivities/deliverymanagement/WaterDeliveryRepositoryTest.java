package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.DataStore;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WaterDeliveryRepositoryTest implements AbstractTest {

  @InjectMocks private WaterDeliveryRepository repository;

  @Mock private DataStore dataStore;

  @Test
  void verifyUpdate() {
    var requestOrder = setupFakeDeliveryOrder();
    repository.update(requestOrder);
    verify(dataStore, times(1)).update(requestOrder);
  }

  @Test
  void verifySave() {
    var requestOrder = setupFakeDeliveryOrder();
    when(dataStore.add(requestOrder)).thenReturn(requestOrder);
    var actual = repository.save(requestOrder);
    verify(dataStore, times(1)).add(requestOrder);
    assertThat(actual.getHash()).isEqualTo(requestOrder.getHash());
  }

  @Test
  void verifyDelete() {
    var requestOrder = setupFakeDeliveryOrder();
    repository.delete(requestOrder);
    verify(dataStore, times(1)).delete(requestOrder);
  }

  @Test
  void verifyFindAll() {
    var requestOrder = setupFakeDeliveryOrder();
    when(dataStore.findByFarmId(requestOrder.getFarmId())).thenReturn(List.of(requestOrder));
    assertThat(repository.find(requestOrder.getFarmId()).size()).isEqualTo(1);
  }

  @Test
  void verifyFindOne() {
    var requestOrder = setupFakeDeliveryOrder();
    when(dataStore.find(requestOrder.getFarmId(), requestOrder.getId()))
        .thenReturn(Optional.of(requestOrder));
    assertThat(repository.find(requestOrder.getFarmId(), requestOrder.getId()).isPresent())
        .isTrue();
  }

  private WaterDeliveryOrder setupFakeDeliveryOrder() {
    LocalDateTime dateOrderReceived = LocalDateTime.parse("2019-09-12T13:45:11");
    LocalDateTime orderStartDate = LocalDateTime.parse("2019-10-10T06:10:11");
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    return createOrderRequest(farmId, dateOrderReceived, orderStartDate, 4);
  }
}
