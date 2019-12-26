package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain;

public enum WaterDeliveryStatus {
  REQUESTED("Requested", true),
  IN_PROGRESS("InProgress", true),
  DELIVERED("Delivered", false),
  CANCELLED("Cancelled", false);

  private final boolean allowCancel;
  private final String description;

  WaterDeliveryStatus(String description, boolean allowCancel) {
    this.description = description;
    this.allowCancel = allowCancel;
  }

  public boolean isAllowCancel() {
    return allowCancel;
  }

  public String getDescription() {
    return description;
  }
}
