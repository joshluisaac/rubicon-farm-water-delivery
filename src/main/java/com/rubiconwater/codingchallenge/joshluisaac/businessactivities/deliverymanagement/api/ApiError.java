package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError {

  private HttpStatus status;

  @JsonProperty("http_status")
  private int httpStatusValue;

  @JsonProperty("date_error_occurred")
  private LocalDateTime dateErrorOccurred;

  @JsonProperty("error_message")
  private String errorMessage;

  @JsonProperty("path")
  private String path;
}
