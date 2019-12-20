package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class JsonMappers {

  private JsonMappers() {}

  public static ObjectReader buildReader() {
    return new ObjectMapper().reader();
  }

  public static ObjectWriter buildWriter() {
    return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writer();
  }
}
