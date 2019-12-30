package com.rubiconwater.codingchallenge.joshluisaac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FarmWaterRequestApplication {

  public static void main(String[] args) {
    SpringApplication.run(FarmWaterRequestApplication.class, args);
  }
}
