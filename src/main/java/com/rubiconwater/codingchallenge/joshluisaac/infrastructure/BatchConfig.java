package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryUtils;
import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BatchDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BatchConfig {

  /**
   * In production this would probably be set from a config/properties file or even from a database.
   *
   * @return
   */
  @Bean
  public BatchDate createBatchDate() {
    LocalDateTime now = LocalDateTime.now();
    log.info("Batch date set to {}", WaterDeliveryUtils.toIsoLocalDateTime(now));
    return new BatchDate(now);
  }
}
