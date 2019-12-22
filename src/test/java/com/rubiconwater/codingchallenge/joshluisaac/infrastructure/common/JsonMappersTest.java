package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.ApiRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class JsonMappersTest implements AbstractTest {

  private static final UUID FARM_ID = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");

  private static final String JSON_SINGLE_REQUEST_BODY =
      "{\n"
          + "  \"farm_id\" : \"1ddeab59-8bb1-4292-8fe4-7a6769411fe5\",\n"
          + "  \"orders\" : [ {\n"
          + "    \"order_start_date\" : \"2019-05-10T06:10:11\",\n"
          + "    \"duration\" : 100\n"
          + "  } ]\n"
          + "}";

  @Test
  void serializeApiRequest() throws JsonProcessingException {
    ApiRequest.Order aOrder = new ApiRequest.Order();
    aOrder.setOrderStartDate(LocalDateTime.parse("2019-05-10T06:10:11"));
    aOrder.setSupplyDuration(100);

    var apiRequest = new ApiRequest(FARM_ID, List.of(aOrder));
    String jsonText =
        JsonMappers.buildWriter().forType(ApiRequest.class).writeValueAsString(apiRequest);
    System.out.println(jsonText);
    assertThat(jsonText.trim()).isEqualTo(JSON_SINGLE_REQUEST_BODY.trim());
  }

  @Test
  void deserializeSingleApiRequest() throws JsonProcessingException {
    ApiRequest.Order aOrder = new ApiRequest.Order();
    aOrder.setOrderStartDate(LocalDateTime.parse("2019-05-10T06:10:11"));
    aOrder.setSupplyDuration(100);

    ApiRequest apiRequest =
        JsonMappers.buildReader().forType(ApiRequest.class).readValue(JSON_SINGLE_REQUEST_BODY);
    var expected = new ApiRequest(FARM_ID, List.of(aOrder));
    assertThat(apiRequest.getFarmId()).isEqualTo(expected.getFarmId());
  }
}
