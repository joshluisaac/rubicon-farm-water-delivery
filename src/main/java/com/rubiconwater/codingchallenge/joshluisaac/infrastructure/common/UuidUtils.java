package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import java.util.UUID;

public final class UuidUtils {

  private UuidUtils() {}

  public static UUID create() {
    return UUID.randomUUID();
  }

  public static UUID toUuid(String uuid) {
    return UUID.fromString(uuid);
  }
}
