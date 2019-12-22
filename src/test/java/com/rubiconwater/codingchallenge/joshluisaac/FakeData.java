package com.rubiconwater.codingchallenge.joshluisaac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryOrder;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.time.LocalDateTime;
import java.util.*;

public class FakeData implements AbstractTest {

  private static final UUID FARM_ID = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
  private static final LocalDateTime ORDER_RECEIVED_DATE =
      LocalDateTime.parse("2019-01-12T13:45:11");
  Map<UUID, List<WaterDeliveryOrder>> cache = new HashMap<>();

  void createData() throws JsonProcessingException {
    LocalDateTime orderStartDate = LocalDateTime.parse("2019-10-10T06:10:11");
    var requestOrder = createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 4);
    List<WaterDeliveryOrder> farmRequests = new ArrayList<>();
    farmRequests.add(requestOrder);
    cache.put(requestOrder.getFarmId(), farmRequests);

    String text =
        JsonMappers.buildWriter()
            .forType(new TypeReference<Map<UUID, List<WaterDeliveryOrder>>>() {})
            .writeValueAsString(cache);

    System.out.println(text);
  }

  public static void main(String[] args) throws JsonProcessingException {

    new FakeData().createData();
  }
}
