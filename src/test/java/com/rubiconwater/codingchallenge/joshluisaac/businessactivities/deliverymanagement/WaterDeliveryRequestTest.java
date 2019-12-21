package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import static org.assertj.core.api.Assertions.*;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WaterDeliveryRequestTest extends AbstractTest {

  private static final UUID FARM_ID = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");

  @Test
  @DisplayName("Should throw an exception if order start date is in the past.")
  void throwException_WhenOrderStartDate_InThePast() {
    // when a farmer submits a water order request
    LocalDateTime dateOrderReceived = LocalDateTime.of(2019, 9, 12, 13, 45, 11);
    LocalDateTime orderStartDate = LocalDateTime.of(2016, 10, 10, 6, 10, 11);
    Throwable throwable =
        catchThrowable(() -> createOrderRequest(FARM_ID, dateOrderReceived, orderStartDate, 4));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Order start date cannot be in the past");
  }

  @Test
  @DisplayName("Should throw an exception when supply duration is negative.")
  void throwException_WhenSupplyDuration_IsNegative() {
    // when a farmer submits a water order request
    LocalDateTime dateOrderReceived = LocalDateTime.of(2019, 9, 12, 13, 45, 11);
    LocalDateTime orderStartDate = LocalDateTime.of(2020, 10, 10, 6, 10, 11);
    Throwable throwable =
        catchThrowable(() -> createOrderRequest(FARM_ID, dateOrderReceived, orderStartDate, -4));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Request supply duration cannot be");
  }

  @Test
  public void isxxx() {
    // when a farmer submits a water order request
    LocalDateTime dateOrderReceived = LocalDateTime.of(2019, 9, 12, 13, 45, 11);
    LocalDateTime orderStartDate = LocalDateTime.of(2019, 10, 10, 6, 10, 11);
    WaterDeliveryRequest requestOrder =
        createOrderRequest(FARM_ID, dateOrderReceived, orderStartDate, 4);

    LocalDateTime dateOrderReceived2 = LocalDateTime.of(2019, 9, 12, 13, 45, 11);
    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 10, 10, 6, 10, 11);
    WaterDeliveryRequest requestOrder2 =
        createOrderRequest(FARM_ID, dateOrderReceived2, orderStartDate2, 400);

    System.out.println(requestOrder.getHash());
    System.out.println(requestOrder2.getHash());
    System.out.println(requestOrder.getFarmId().toString());
    System.out.println(requestOrder.isBetweenTimeFrameOf(requestOrder2));
  }
}
