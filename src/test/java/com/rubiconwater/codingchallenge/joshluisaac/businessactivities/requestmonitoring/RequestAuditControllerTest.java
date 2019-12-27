package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.requestmonitoring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rubiconwater.codingchallenge.joshluisaac.FakeData;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestAction;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestObserver;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RequestAuditController.class)
public class RequestAuditControllerTest {

  @MockBean private RequestAuditService requestAuditService;
  @MockBean private RequestObserver requestObserver;
  @Autowired private MockMvc mockMvc;

  @Test
  public void shouldGet_AllRequests() throws Exception {
    List<RequestAction> requestActions =
        JsonMappers.buildReader()
            .forType(new TypeReference<List<RequestAction>>() {})
            .readValue(FakeData.REQUEST_LOG);
    when(requestAuditService.getRequests()).thenReturn(requestActions);
    MvcResult responseResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/requests"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    assertThat(responseBody).contains("92693768-9fdd-494f-b79b-778d64e4ec2f");
  }

  @Test
  public void shouldGet_SpecificRequest() throws Exception {
    List<RequestAction> requestActions =
        JsonMappers.buildReader()
            .forType(new TypeReference<List<RequestAction>>() {})
            .readValue(FakeData.REQUEST_LOG);
    when(requestAuditService.getRequest("92693768-9fdd-494f-b79b-778d64e4ec2f"))
        .thenReturn(requestActions.get(0));
    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                "/api/requests/{id}", "92693768-9fdd-494f-b79b-778d64e4ec2f"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }
}
