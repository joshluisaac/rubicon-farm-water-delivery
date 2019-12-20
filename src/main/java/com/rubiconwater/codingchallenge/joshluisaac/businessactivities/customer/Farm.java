package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.customer;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.AbstractEntity;
import lombok.Builder;

public class Farm extends AbstractEntity {

  private final String name;
  private final String location;

  @Builder
  public Farm(String name, String location) {
    super(UuidUtils.create());
    this.name = name;
    this.location = location;
  }
}
