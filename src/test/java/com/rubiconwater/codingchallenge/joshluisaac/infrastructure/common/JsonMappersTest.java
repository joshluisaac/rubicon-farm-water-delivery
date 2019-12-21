package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryRequest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.ApiDeliveryRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class JsonMappersTest implements AbstractTest {

  private static final UUID FARM_ID = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");

  private static final LocalDateTime ORDER_RECEIVED_DATE =
      LocalDateTime.of(2019, 1, 12, 13, 45, 11);

  private static final String JSON_SINGLE_REQUEST_BODY = "{\n"
          + "  \"farm_id\" : \"1ddeab59-8bb1-4292-8fe4-7a6769411fe5\",\n"
          + "  \"order_start_date\" : \"2019-05-10T06:10:11\",\n"
          + "  \"duration\" : 100\n"
          + "}\n";

  @Test
  void serializeApiRequest() throws JsonProcessingException {
    ApiDeliveryRequest apiRequest =
        new ApiDeliveryRequest(FARM_ID, LocalDateTime.parse("2019-05-10T06:10:11"), 100);
    String jsonText =
        JsonMappers.buildWriter().forType(ApiDeliveryRequest.class).writeValueAsString(apiRequest);
    assertThat(jsonText.trim()).isEqualTo(JSON_SINGLE_REQUEST_BODY.trim());
  }

  @Test
  void deserializeSingleApiRequest() throws JsonProcessingException {
    ApiDeliveryRequest apiRequest = JsonMappers.buildReader().forType(ApiDeliveryRequest.class).readValue(JSON_SINGLE_REQUEST_BODY);
    ApiDeliveryRequest expected = new ApiDeliveryRequest(FARM_ID, LocalDateTime.parse("2019-05-10T06:10:11"), 100);
    assertThat(apiRequest).isEqualToComparingFieldByField(expected);
  }

  @Test
  void deserializeMultipleRequests() {
    LocalDateTime orderStartDate = LocalDateTime.of(2019, 5, 10, 6, 10, 11);
    WaterDeliveryRequest exitingOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 100);
    JsonMappers.buildReader();
  }
}
