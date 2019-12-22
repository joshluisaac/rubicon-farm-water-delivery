package com.rubiconwater.codingchallenge.joshluisaac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FarmWaterRequestApplication {

  public static void main(String[] args) {
    SpringApplication.run(FarmWaterRequestApplication.class, args);
  }
}
