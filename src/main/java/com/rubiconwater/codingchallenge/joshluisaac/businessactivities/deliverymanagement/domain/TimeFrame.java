package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@AllArgsConstructor
@JsonIgnoreType
public class TimeFrame {

  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public boolean isBetweenTimeFrameOf(TimeFrame timeFrame) {
    boolean startDateOverlaps =
        isEqualOrAfter(timeFrame.getStartDate()) && isEqualOrBefore(timeFrame.getStartDate());
    boolean endDateOverlaps =
        isEqualOrAfter(timeFrame.getEndDate()) && isEqualOrBefore(timeFrame.getEndDate());
    return (startDateOverlaps || endDateOverlaps);
  }

  private boolean isEqualOrAfter(LocalDateTime date) {
    return (date.isEqual(startDate) || date.isAfter(startDate));
  }

  private boolean isEqualOrBefore(LocalDateTime date) {
    return (date.isEqual(endDate) || date.isBefore(endDate));
  }
}
