package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import springfox.documentation.spi.DocumentationType;

public class Swagger2ConfigTest {

  @Test
  void testSwaggerConfig() {
    Assertions.assertThat(new Swagger2Config().swaggerConfig().getDocumentationType())
        .isEqualTo(DocumentationType.SWAGGER_2);
  }
}
