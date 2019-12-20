package com.rubiconwater.codingchallenge.joshluisaac.sharedkernel;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractEntity implements BaseEntity {

  private final UUID id;
}
