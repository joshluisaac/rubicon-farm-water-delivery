package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Builder
@Value
public class ApiResponse {

  @JsonProperty("http_status")
  private HttpStatus status;

  @JsonProperty("http_response_code")
  private int httpResponseCode;

  @JsonProperty("request_date")
  private LocalDateTime requestDate;

  @JsonProperty(value = "water_delivery_orders")
  @Singular
  private List<WaterDeliveryResponse> waterDeliveryResponses;
}
