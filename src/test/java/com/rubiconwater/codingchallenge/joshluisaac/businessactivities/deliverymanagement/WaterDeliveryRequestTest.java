package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import static org.assertj.core.api.Assertions.*;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WaterDeliveryRequestTest implements AbstractTest {

  private static final UUID FARM_ID = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
  private static final LocalDateTime ORDER_RECEIVED_DATE =
      LocalDateTime.of(2019, 1, 12, 13, 45, 11);

  /*      System.out.println(
            WaterDeliveryUtils.toIsoLocalDateTime(exitingOrder.getTimeFrame().getStartDate()));
  System.out.println(
          WaterDeliveryUtils.toIsoLocalDateTime(exitingOrder.getTimeFrame().getEndDate()));

  System.out.println(
          WaterDeliveryUtils.toIsoLocalDateTime(newOrder.getTimeFrame().getStartDate()));
  System.out.println(WaterDeliveryUtils.toIsoLocalDateTime(newOrder.getTimeFrame().getEndDate()));*/

  @Test
  @DisplayName("Should throw an exception if order start date is in the past.")
  void throwException_WhenOrderStartDate_InThePast() {

    LocalDateTime orderStartDate = LocalDateTime.of(2016, 10, 10, 6, 10, 11);
    Throwable throwable =
        catchThrowable(() -> createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 4));

    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Order start date cannot be in the past");
  }

  @Test
  @DisplayName("Should throw an exception when supply duration is negative.")
  void throwException_WhenSupplyDuration_IsNegative() {

    LocalDateTime orderStartDate = LocalDateTime.of(2020, 10, 10, 6, 10, 11);
    Throwable throwable =
        catchThrowable(() -> createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, -4));

    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Request supply duration cannot be");
  }

  @Test
  void shouldReturn_True_WhenNewStartDate_EqualsExistingTimeFrame() {

    LocalDateTime orderStartDate = LocalDateTime.of(2019, 2, 10, 1, 10, 11);
    WaterDeliveryRequest requestOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 4);

    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 2, 10, 1, 10, 11);
    WaterDeliveryRequest requestOrder2 =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 15);

    assertThat(requestOrder.getTimeFrame().isBetweenTimeFrameOf(requestOrder2.getTimeFrame()))
        .isTrue();
  }

  @Test
  void shouldReturn_True_WhenNewStartDate_MatchesExistingTimeFrame() {

    LocalDateTime orderStartDate = LocalDateTime.of(2019, 2, 10, 1, 10, 11);
    WaterDeliveryRequest requestOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 4);

    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 2, 10, 3, 10, 11);
    WaterDeliveryRequest requestOrder2 =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 15);

    assertThat(requestOrder.getTimeFrame().isBetweenTimeFrameOf(requestOrder2.getTimeFrame()))
        .isTrue();
  }

  /**
   * When an existing order is booked in the future and the end date of a new order is collides with
   * this time frame.
   *
   * <p>For example, <code>
   *  Existing order start date: 2019-05-10T06:10:11
   *  Existing end start date: 2019-05-14T10:10:11
   *  New order start date: 2019-05-08T06:10:11
   *  New order end date: 2019-05-12T10:10:11
   * </code>
   *
   * <p>Notice that, 2019-05-12T10:10:11 is between 2019-05-10T06:10:11 and 2019-05-14T10:10:11
   */
  @Test
  void shouldReturn_True_WhenEndDateOverlaps_ExistingTimeFrame() {

    LocalDateTime orderStartDate = LocalDateTime.of(2019, 5, 10, 6, 10, 11);
    WaterDeliveryRequest exitingOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 100);

    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 5, 8, 6, 10, 11);
    WaterDeliveryRequest newOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 100);
    assertThat(exitingOrder.getTimeFrame().isBetweenTimeFrameOf(newOrder.getTimeFrame())).isTrue();
  }

  @Test
  // @OnErrorLogSituation or @LogSituationOnError
  void shouldReturn_True_WhenEndDateMatches_EndDateOfExistingTimeFrame() {

    LocalDateTime orderStartDate = LocalDateTime.of(2019, 5, 10, 6, 10, 11);
    WaterDeliveryRequest exitingOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 100);

    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 5, 8, 6, 10, 11);
    WaterDeliveryRequest newOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 148);
    assertThat(exitingOrder.getTimeFrame().isBetweenTimeFrameOf(newOrder.getTimeFrame())).isTrue();
  }

  @Test
  void shouldReturn_False_WhenTimeFrameNotOverlap() {

    LocalDateTime orderStartDate = LocalDateTime.of(2019, 2, 10, 6, 10, 11);
    WaterDeliveryRequest exitingOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 4);

    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 9, 10, 6, 10, 11);
    WaterDeliveryRequest newOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 100);

    assertThat(exitingOrder.getTimeFrame().isBetweenTimeFrameOf(newOrder.getTimeFrame())).isFalse();
  }
}
