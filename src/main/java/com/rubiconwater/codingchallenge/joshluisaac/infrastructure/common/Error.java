package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {

  private final String code;
  private final String description;
}
