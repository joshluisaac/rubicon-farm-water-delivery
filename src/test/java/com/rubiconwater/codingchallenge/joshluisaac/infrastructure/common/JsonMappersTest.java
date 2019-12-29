package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.AcceptOrderRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class JsonMappersTest implements AbstractTest {

  private static final UUID FARM_ID = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");

  private static final String JSON_REQUEST_BODY =
      "{\n"
          + "  \"farm_id\" : \"1ddeab59-8bb1-4292-8fe4-7a6769411fe5\",\n"
          + "  \"orders\" : [ {\n"
          + "    \"order_start_date\" : \"2019-05-10T06:10:11\",\n"
          + "    \"duration\" : 100\n"
          + "  } ]\n"
          + "}";

  @Test
  void serializeApiRequest() throws JsonProcessingException {
    AcceptOrderRequest.Order aOrder = new AcceptOrderRequest.Order();
    aOrder.setOrderStartDate(LocalDateTime.parse("2019-05-10T06:10:11"));
    aOrder.setSupplyDuration(100);
    var acceptOrderRequest = new AcceptOrderRequest(FARM_ID, List.of(aOrder));
    String jsonText =
        JsonMappers.buildWriter()
            .forType(AcceptOrderRequest.class)
            .writeValueAsString(acceptOrderRequest);
    assertThat(jsonText.trim()).isEqualTo(JSON_REQUEST_BODY.trim());
  }

  @Test
  void deserializeAcceptOrderRequest() throws JsonProcessingException {
    AcceptOrderRequest.Order aOrder = new AcceptOrderRequest.Order();
    aOrder.setOrderStartDate(LocalDateTime.parse("2019-05-10T06:10:11"));
    aOrder.setSupplyDuration(100);
    AcceptOrderRequest acceptOrderRequest =
        JsonMappers.buildReader().forType(AcceptOrderRequest.class).readValue(JSON_REQUEST_BODY);
    var expected = new AcceptOrderRequest(FARM_ID, List.of(aOrder));
    assertThat(acceptOrderRequest.getFarmId()).isEqualTo(expected.getFarmId());
  }
}
