package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@AllArgsConstructor
@JsonIgnoreType
public class DeliveryTimeWindow {

  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public boolean isBetweenTimeFrameOf(DeliveryTimeWindow deliveryTimeWindow) {
    boolean startDateOverlaps =
        isEqualOrAfter(deliveryTimeWindow.getStartDate())
            && isEqualOrBefore(deliveryTimeWindow.getStartDate());
    boolean endDateOverlaps =
        isEqualOrAfter(deliveryTimeWindow.getEndDate())
            && isEqualOrBefore(deliveryTimeWindow.getEndDate());
    return (startDateOverlaps || endDateOverlaps);
  }

  private boolean isEqualOrAfter(LocalDateTime date) {
    return (date.isEqual(startDate) || date.isAfter(startDate));
  }

  private boolean isEqualOrBefore(LocalDateTime date) {
    return (date.isEqual(endDate) || date.isBefore(endDate));
  }
}
