package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RequestActionTest {

  @Test
  void equalsComparison_ShouldReturnTrue() {
    RequestAction requestAction = RequestAction.builder().build();
    RequestAction requestAction2 = requestAction;
    System.out.println(requestAction.hashCode());
    assertThat(requestAction.equals(requestAction2)).isTrue();
  }

  @Test
  void hashCodeComparison_ShouldReturnTrue() {
    RequestAction requestAction = RequestAction.builder().build();
    RequestAction requestAction2 = requestAction;
    assertThat(requestAction.hashCode() == requestAction2.hashCode()).isTrue();
  }
}
