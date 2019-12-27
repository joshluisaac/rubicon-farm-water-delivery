package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.requestmonitoring;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rubiconwater.codingchallenge.joshluisaac.FakeData;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.Errors;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestAction;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestObserver;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RequestAuditServiceTest {

  @MockBean private RequestObserver requestObserver;

  @Autowired private RequestAuditService service;

  @Configuration
  @Import(RequestAuditService.class)
  static class Config {}

  @Test
  void shouldReturn_RequestedActions() throws JsonProcessingException {
    List<RequestAction> requestActions =
        JsonMappers.buildReader()
            .forType(new TypeReference<List<RequestAction>>() {})
            .readValue(FakeData.REQUEST_LOG);
    given(requestObserver.getRequestActions()).willReturn(requestActions);
    assertThat(service.getRequests().size()).isEqualTo(2);
  }

  @Test
  void shouldReturn_RequestedAction() throws JsonProcessingException {
    List<RequestAction> requestActions =
        JsonMappers.buildReader()
            .forType(new TypeReference<List<RequestAction>>() {})
            .readValue(FakeData.REQUEST_LOG);
    given(requestObserver.getRequestActions()).willReturn(requestActions);
    assertThat(service.getRequest("943d1664-19c2-4fcf-ae73-b53eed31c9c5").getRequestId())
        .isEqualTo(UuidUtils.toUuid("943d1664-19c2-4fcf-ae73-b53eed31c9c5"));
  }

  @Test
  void shouldThrowException_WhenRequestedAction_NotFound() throws JsonProcessingException {
    List<RequestAction> requestActions =
        JsonMappers.buildReader()
            .forType(new TypeReference<List<RequestAction>>() {})
            .readValue(FakeData.REQUEST_LOG);
    given(requestObserver.getRequestActions()).willReturn(requestActions);
    Throwable throwable =
        catchThrowable(() -> service.getRequest("943d1664-19c2-4fcf-ae73-b53eed31c9c9"));
    assertThat(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(
            String.format(
                Errors.REQUEST_LOG_NOT_FOUND.getDescription(),
                "943d1664-19c2-4fcf-ae73-b53eed31c9c9"));
  }
}
