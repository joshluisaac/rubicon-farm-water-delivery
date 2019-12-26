package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CancelOrderRequestTest {

  private static final String JSON_REQUEST_BODY =
      "{\n"
          + "\t\"farm_id\": \"975eebdd-b9fa-493b-ac55-273383b02c86\",\n"
          + "\t\"orders\": [\n"
          + "\t  \"1334d383-78a1-4cd9-ac9d-c5faf980d6b4\", \n"
          + "\t  \"9ef5c0f7-1d1f-4955-a051-ce9ba1eb2812\", \n"
          + "\t  \"736038b4-481a-41a7-96d6-be9f3fd95fc4\"\n"
          + "\t]\n"
          + "}";

  @Test
  void deserializeCancelOrderRequest() throws JsonProcessingException {
    CancelOrderRequest expected =
        new CancelOrderRequest(
            UuidUtils.toUuid("975eebdd-b9fa-493b-ac55-273383b02c86"),
            List.of(
                UuidUtils.toUuid("1334d383-78a1-4cd9-ac9d-c5faf980d6b4"),
                UuidUtils.toUuid("9ef5c0f7-1d1f-4955-a051-ce9ba1eb2812"),
                UuidUtils.toUuid("736038b4-481a-41a7-96d6-be9f3fd95fc4")));
    CancelOrderRequest actual =
        JsonMappers.buildReader().forType(CancelOrderRequest.class).readValue(JSON_REQUEST_BODY);
    assertThat(actual.getFarmId()).isEqualTo(expected.getFarmId());
  }

  @Test
  void deserializeCancelOrderRequestSize() throws JsonProcessingException {
    CancelOrderRequest expected =
        new CancelOrderRequest(
            UuidUtils.toUuid("975eebdd-b9fa-493b-ac55-273383b02c86"),
            List.of(
                UuidUtils.toUuid("1334d383-78a1-4cd9-ac9d-c5faf980d6b4"),
                UuidUtils.toUuid("9ef5c0f7-1d1f-4955-a051-ce9ba1eb2812"),
                UuidUtils.toUuid("736038b4-481a-41a7-96d6-be9f3fd95fc4")));
    CancelOrderRequest actual =
        JsonMappers.buildReader().forType(CancelOrderRequest.class).readValue(JSON_REQUEST_BODY);
    assertThat(actual.getDeliveryIds().size()).isEqualTo(expected.getDeliveryIds().size());
  }
}
