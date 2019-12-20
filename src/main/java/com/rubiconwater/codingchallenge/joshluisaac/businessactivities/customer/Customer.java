package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.customer;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.AbstractEntity;
import java.util.List;
import lombok.Builder;
import lombok.Singular;

public class Customer extends AbstractEntity {

  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String emailValue;
  @Singular private final List<Farm> farms;

  @Builder
  public Customer(
      String firstName, String middleName, String lastName, String emailValue, List<Farm> farms) {
    super(UuidUtils.create());
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.emailValue = emailValue;
    this.farms = farms;
  }
}
