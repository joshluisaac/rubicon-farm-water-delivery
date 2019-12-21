package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonMappers {

  private JsonMappers() {}

  public static ObjectReader buildReader() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .reader();
  }

  /**
   * Jackson serialization feature. Represents {@code LocalDateTime} in ISO-8601
   *
   * @return
   */
  public static ObjectWriter buildWriter() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(SerializationFeature.INDENT_OUTPUT)
        .writer();
  }
}
