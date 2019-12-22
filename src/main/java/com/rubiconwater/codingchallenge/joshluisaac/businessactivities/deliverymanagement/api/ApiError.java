package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

  private HttpStatus status;
}
