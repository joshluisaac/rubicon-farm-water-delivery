package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

public enum WaterDeliveryStatus {
  REQUESTED (true),
  IN_PROGRESS(true),
  DELIVERED(false),
  CANCELLED(false);

  private final boolean allowCancel;

    WaterDeliveryStatus(boolean allowCancel) {
        this.allowCancel = allowCancel;
    }


    public boolean isAllowCancel() {
        return allowCancel;
    }
}
