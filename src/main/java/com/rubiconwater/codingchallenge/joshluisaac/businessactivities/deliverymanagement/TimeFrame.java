package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class TimeFrame {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
