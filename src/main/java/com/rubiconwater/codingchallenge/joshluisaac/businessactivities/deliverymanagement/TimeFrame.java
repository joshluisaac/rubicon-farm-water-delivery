package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TimeFrame {

  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
