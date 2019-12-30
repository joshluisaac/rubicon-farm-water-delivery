package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import static org.assertj.core.api.Assertions.*;

import com.rubiconwater.codingchallenge.joshluisaac.sharedkernel.BatchDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class BatchConfigTest {

  @Test
  public void testBatchConfig() {
    BatchDate batchDate = new BatchConfig().createBatchDate();
    batchDate.setBatchDate(LocalDateTime.parse("2018-10-10T09:09:09"));
    assertThat(batchDate.getBatchDate()).isEqualTo(LocalDateTime.parse("2018-10-10T09:09:09"));
  }
}
