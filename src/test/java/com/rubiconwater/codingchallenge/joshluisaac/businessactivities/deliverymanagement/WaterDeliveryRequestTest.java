package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import static org.assertj.core.api.Assertions.*;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.WaterDeliveryUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WaterDeliveryRequestTest implements AbstractTest {

  private static final UUID FARM_ID = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
  private static final LocalDateTime ORDER_RECEIVED_DATE =
      LocalDateTime.of(2019, 1, 12, 13, 45, 11);

  void logSituation(WaterDeliveryRequest exitingOrder, WaterDeliveryRequest newOrder) {
    System.out.println(
        WaterDeliveryUtils.toIsoLocalDateTime(exitingOrder.getTimeFrame().getStartDate()));
    System.out.println(
        WaterDeliveryUtils.toIsoLocalDateTime(exitingOrder.getTimeFrame().getEndDate()));

    System.out.println(
        WaterDeliveryUtils.toIsoLocalDateTime(newOrder.getTimeFrame().getStartDate()));
    System.out.println(WaterDeliveryUtils.toIsoLocalDateTime(newOrder.getTimeFrame().getEndDate()));
  }

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

  /**
   * When the start date of a new order is collides with the start date of an existing order.
   * For example, <code>
   *
   * Existing order start date: 2019-02-10T01:10:11
   * Existing order end date: 2019-02-10T05:10:11
   * New order start date: 2019-02-10T01:10:11
   * New order end date: 2019-02-10T16:10:11
   *  </code>
   *
   * <p>Notice that, 2019-02-10T01:10:11 collides with Existing order start date:
   * 2019-02-10T01:10:11
   */
  @Test
  void shouldReturn_True_WhenStartDateMatches_StartDateOfExistingTimeFrame() {

    LocalDateTime orderStartDate = LocalDateTime.of(2019, 2, 10, 1, 10, 11);
    WaterDeliveryRequest requestOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 4);

    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 2, 10, 1, 10, 11);
    WaterDeliveryRequest requestOrder2 =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 15);

    // logSituation(requestOrder, requestOrder2);

    assertThat(requestOrder.getTimeFrame().isBetweenTimeFrameOf(requestOrder2.getTimeFrame()))
        .isTrue();
  }

  @Test
  void shouldReturn_True_WhenStartDateOverlapsInto_ExistingTimeFrame() {

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
     * When the start date of a new order is collides with the end date of an existing order.
     * For example, <code>
     *
     * Existing order start date: 2019-02-10T01:10:11
     * Existing order end date: 2019-02-10T05:10:11
     *
     * New order start date: 2019-02-10T05:10:11
     * New order end date: 2019-02-10T16:10:11
     *  </code>
     *
     * <p>Notice that, 2019-02-10T05:10:11 collides with Existing order end date:
     * 2019-02-10T05:10:11
     */
    @Test
    void shouldReturn_True_WhenStartDateMatches_EndDateOfExistingTimeFrame() {

        LocalDateTime orderStartDate = LocalDateTime.of(2019, 2, 10, 1, 10, 11);
        WaterDeliveryRequest requestOrder =
                createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 4);

        LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 2, 10, 5, 10, 11);
        WaterDeliveryRequest requestOrder2 =
                createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 15);

        // logSituation(requestOrder, requestOrder2);

        assertThat(requestOrder.getTimeFrame().isBetweenTimeFrameOf(requestOrder2.getTimeFrame()))
                .isTrue();
    }


  /**
   * When the end date of a new order is collides with the start date of an existing order.
   *
   * <p>For example, <code>
   *  Existing order start date: 2019-05-10T06:10:11
   *  Existing end start date: 2019-05-14T10:10:11
   *
   *  New order start date: 2019-05-08T06:10:11
   *  New order end date: 2019-05-10T10:10:11
   * </code>
   *
   * <p>Notice that, 2019-05-14T10:10:11 collides with Existing start start date:
   * 2019-05-10T10:10:11
   */
  @Test
  // @OnErrorLogSituation or @LogSituationOnError
  void shouldReturn_True_WhenEndDateMatches_StartDateOfExistingTimeFrame() {

    LocalDateTime orderStartDate = LocalDateTime.of(2019, 5, 10, 6, 10, 11);
    WaterDeliveryRequest exitingOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 100);

    LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 5, 8, 6, 10, 11);
    WaterDeliveryRequest newOrder =
        createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 48);

    logSituation(exitingOrder, newOrder);
    assertThat(exitingOrder.getTimeFrame().isBetweenTimeFrameOf(newOrder.getTimeFrame())).isTrue();
  }

    /**
     * When the end date of a new order overlaps into an existing order.
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
    void shouldReturn_True_WhenEndDateOverlapsInto_ExistingTimeFrame() {

        LocalDateTime orderStartDate = LocalDateTime.of(2019, 5, 10, 6, 10, 11);
        WaterDeliveryRequest exitingOrder =
                createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate, 100);

        LocalDateTime orderStartDate2 = LocalDateTime.of(2019, 5, 8, 6, 10, 11);
        WaterDeliveryRequest newOrder =
                createOrderRequest(FARM_ID, ORDER_RECEIVED_DATE, orderStartDate2, 100);
        assertThat(exitingOrder.getTimeFrame().isBetweenTimeFrameOf(newOrder.getTimeFrame())).isTrue();
    }

    /**
     * When the end date of a new order is collides with the end date of an existing order.
     *
     * <p>For example, <code>
     *  Existing order start date: 2019-05-10T06:10:11
     *  Existing end start date: 2019-05-14T10:10:11
     *  New order start date: 2019-05-08T06:10:11
     *  New order end date: 2019-05-14T10:10:11
     * </code>
     *
     * <p>Notice that, 2019-05-14T10:10:11 collides with Existing end start date: 2019-05-14T10:10:11
     */
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






  //@Test
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
