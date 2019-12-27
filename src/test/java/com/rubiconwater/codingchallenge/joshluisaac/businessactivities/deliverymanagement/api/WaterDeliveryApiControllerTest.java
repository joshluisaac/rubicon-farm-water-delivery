package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rubiconwater.codingchallenge.joshluisaac.AbstractTest;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryOrder;
import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.domain.WaterDeliveryService;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.Errors;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestObserver;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WaterDeliveryApiController.class)
public class WaterDeliveryApiControllerTest implements AbstractTest {

  @MockBean private WaterDeliveryService deliveryService;
  @MockBean private ApiMapper apiMapper;
  @MockBean private RequestObserver requestObserver;
  @Autowired private MockMvc mockMvc;

  private static final String ACCEPT_ORDER_REQUEST =
      "{\n"
          + "  \"farm_id\": \"975eebdd-b9fa-493b-ac55-273383b02c86\",\n"
          + "  \"orders\": [\n"
          + "\n"
          + "    {\n"
          + "      \"order_start_date\": \"2019-12-20T06:10:11\",\n"
          + "      \"duration\": 7\n"
          + "    },\n"
          + "\n"
          + "    {\n"
          + "      \"order_start_date\": \"2020-01-10T06:10:11\",\n"
          + "      \"duration\": 4\n"
          + "    },\n"
          + "\n"
          + "    {\n"
          + "      \"order_start_date\": \"2020-01-15T06:10:11\",\n"
          + "      \"duration\": 10\n"
          + "    }\n"
          + "  ]\n"
          + "\n"
          + "}";

  private static final String CANCEL_ORDER_REQUEST =
      "{\n"
          + "  \"farm_id\": \"975eebdd-b9fa-493b-ac55-273383b02c86\",\n"
          + "  \"orders\": [\n"
          + "    \"1334d383-78a1-4cd9-ac9d-c5faf980d6b4\",\n"
          + "    \"9ef5c0f7-1d1f-4955-a051-ce9ba1eb2812\",\n"
          + "    \"736038b4-481a-41a7-96d6-be9f3fd95fc4\"\n"
          + "  ]\n"
          + "}";

  private static final String UNREADABLE_REQUEST =
      "{\n"
          + "  \"farm_id\": \"975eebdd-b9fa-493b-ac55-273383b02c86\"\n"
          + "  \"orders\": [\n"
          + "\n"
          + "    {\n"
          + "      \"order_start_date\": \"2019-12-20T06:10:11\",\n"
          + "      \"duration\": 7\n"
          + "    },\n"
          + "\n"
          + "    {\n"
          + "      \"order_start_date\": \"2020-01-10T06:10:11\",\n"
          + "      \"duration\": 4\n"
          + "    },\n"
          + "\n"
          + "    {\n"
          + "      \"order_start_date\": \"2020-01-15T06:10:11\",\n"
          + "      \"duration\": 10\n"
          + "    }\n"
          + "  ]\n"
          + "\n"
          + "}";

  private static final String INVALID_REQUEST = "{}";

  @Test
  public void shouldReturn_MethodNotAllowed_WhenFarmIdIsEmpty() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(deliveryService.getDeliveryOrders(requestOrder.getFarmId()))
        .thenReturn(List.of(requestOrder));
    MvcResult responseResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/farmers/{farmId}", ""))
            .andExpect(status().isMethodNotAllowed())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    ApiError apiError = JsonMappers.buildReader().forType(ApiError.class).readValue(responseBody);
    assertThat(apiError.getErrorMessage()).isEqualTo("Request method 'GET' not supported");
  }

  @Test
  public void shouldReturn_NotFound_WhenFarmOrdersIsEmpty() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(deliveryService.getDeliveryOrders(requestOrder.getFarmId()))
        .thenReturn(Collections.emptyList());
    MvcResult responseResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(
                    "/api/farmers/{farmId}", "975eebdd-b9fa-493b-ac55-273383b02c86"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    ApiError apiError = JsonMappers.buildReader().forType(ApiError.class).readValue(responseBody);
    assertThat(apiError.getErrorMessage()).contains("975eebdd-b9fa-493b-ac55-273383b02c86");
  }

  @Test
  public void shouldReturn_BadRequest_OnMissingServletRequestParameter() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(deliveryService.getDeliveryOrder(any(), any())).thenReturn(requestOrder);
    MvcResult responseResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/farmers")
                    .content(CANCEL_ORDER_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    ApiError apiError = JsonMappers.buildReader().forType(ApiError.class).readValue(responseBody);
    assertThat(apiError.getErrorMessage())
        .isEqualTo("Required boolean parameter 'cancel' is not present");
  }

  @Test
  public void shouldReturn_BadRequest_WhenSameOrderIsPlacedTwiceOrMore() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(apiMapper.toDeliveryOrder(any(), any())).thenReturn(requestOrder);
    when(deliveryService.acceptOrder(any()))
        .thenThrow(
            new IllegalArgumentException(
                String.format(
                    Errors.EXISTING_ORDER_DUPLICATION.getDescription(),
                    requestOrder.getOrderStartDate(),
                    requestOrder.getSupplyDuration())));
    ResultActions postResultActions = postResultActions();
    postResultActions.andExpect(status().isBadRequest());
  }

  private ResultActions postResultActions() throws Exception {
    return mockMvc.perform(
        MockMvcRequestBuilders.post("/api/farmers")
            .content(ACCEPT_ORDER_REQUEST)
            .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void shouldReturn_UnSupportedMediaType_WhenContentTypeIsNotJson() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(apiMapper.toDeliveryOrder(any(), any())).thenReturn(requestOrder);
    when(deliveryService.acceptOrder(any())).thenReturn(requestOrder);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/farmers")
                .content(ACCEPT_ORDER_REQUEST)
                .contentType(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(status().isUnsupportedMediaType())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  @Test
  public void shouldReturn_BadRequest_OnMethodArgumentTypeMismatch() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(deliveryService.getDeliveryOrder(any(), any())).thenReturn(requestOrder);
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/farmers")
                .param("cancel", "someRandomText")
                .content(CANCEL_ORDER_REQUEST)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  @Test
  public void shouldReturn_BadRequest_WhenHttpMessageNotReadable() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(apiMapper.toDeliveryOrder(any(), any())).thenReturn(requestOrder);
    when(deliveryService.acceptOrder(any())).thenReturn(requestOrder);
    MvcResult responseResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/farmers")
                    .content(UNREADABLE_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    ApiError apiError = JsonMappers.buildReader().forType(ApiError.class).readValue(responseBody);
    assertThat(apiError.getErrorMessage())
        .isEqualTo(Errors.REQUEST_BODY_DESERIALIZATION_ERROR_NOT_READABLE.getDescription());
  }

  @Test
  public void shouldReturn_BadRequest_WhenRequestBodyIsInvalid() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(apiMapper.toDeliveryOrder(any(), any())).thenReturn(requestOrder);
    when(deliveryService.acceptOrder(any())).thenReturn(requestOrder);
    MvcResult responseResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/farmers")
                    .content(INVALID_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    ApiError apiError = JsonMappers.buildReader().forType(ApiError.class).readValue(responseBody);
    assertThat(apiError.getErrorMessage())
        .isEqualTo(Errors.REQUEST_BODY_DESERIALIZATION_ERROR_NOT_VALID.getDescription());
  }

  @Test
  public void shouldCheckResponse_OnGetAllDeliveryOrders() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(deliveryService.getDeliveryOrders(requestOrder.getFarmId()))
        .thenReturn(List.of(requestOrder));
    MvcResult responseResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(
                    "/api/farmers/{farmId}", "1ddeab59-8bb1-4292-8fe4-7a6769411fe5"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    ApiResponse apiResponse =
        JsonMappers.buildReader().forType(ApiResponse.class).readValue(responseBody);
    assertThat(apiResponse.getWaterDeliveryResponses().size()).isGreaterThan(0);
  }

  @Test
  public void shouldCheckResponse_OnGetDeliveryOrder() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    var requestOrderId = requestOrder.getId();
    when(deliveryService.getDeliveryOrder(requestOrder.getFarmId(), requestOrderId))
        .thenReturn(requestOrder);
    MvcResult responseResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(
                    "/api/farmers/{farmId}/orders/{orderId}",
                    "1ddeab59-8bb1-4292-8fe4-7a6769411fe5",
                    requestOrderId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    assertThat(responseBody).contains(requestOrderId.toString());
  }

  @Test
  public void shouldPlaceAnOrder() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(apiMapper.toDeliveryOrder(any(), any())).thenReturn(requestOrder);
    when(deliveryService.acceptOrder(any())).thenReturn(requestOrder);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/farmers")
                .content(ACCEPT_ORDER_REQUEST)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  @Test
  public void shouldUpdateAnOrder() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(deliveryService.getDeliveryOrder(any(), any())).thenReturn(requestOrder);
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/farmers")
                .param("cancel", "true")
                .content(CANCEL_ORDER_REQUEST)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
    verify(deliveryService, times(3)).cancelOrder(any());
  }

  @Test
  public void shouldReturn_BadRequest_WhenCancelRequestParamNotTrue() throws Exception {
    var requestOrder = setupFakeDeliveryOrder();
    when(deliveryService.getDeliveryOrder(any(), any())).thenReturn(requestOrder);
    MvcResult responseResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/farmers")
                    .param("cancel", "false")
                    .content(CANCEL_ORDER_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String responseBody = responseResult.getResponse().getContentAsString();
    ApiError apiError = JsonMappers.buildReader().forType(ApiError.class).readValue(responseBody);
    assertThat(apiError.getErrorMessage()).isEqualTo(Errors.CANCEL_NOT_TRUE.getDescription());
  }

  private WaterDeliveryOrder setupFakeDeliveryOrder() {
    LocalDateTime dateOrderReceived = LocalDateTime.parse("2019-09-12T13:45:11");
    LocalDateTime orderStartDate = LocalDateTime.parse("2019-10-10T06:10:11");
    UUID farmId = UuidUtils.toUuid("1ddeab59-8bb1-4292-8fe4-7a6769411fe5");
    return createOrderRequest(farmId, dateOrderReceived, orderStartDate, 4);
  }
}
