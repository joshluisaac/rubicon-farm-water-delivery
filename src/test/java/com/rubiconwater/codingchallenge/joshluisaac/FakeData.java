package com.rubiconwater.codingchallenge.joshluisaac;

public class FakeData implements AbstractTest {

  public static final String ACCEPT_ORDER_REQUEST =
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

  public static final String CANCEL_ORDER_REQUEST =
      "{\n"
          + "  \"farm_id\": \"975eebdd-b9fa-493b-ac55-273383b02c86\",\n"
          + "  \"orders\": [\n"
          + "    \"1334d383-78a1-4cd9-ac9d-c5faf980d6b4\",\n"
          + "    \"9ef5c0f7-1d1f-4955-a051-ce9ba1eb2812\",\n"
          + "    \"736038b4-481a-41a7-96d6-be9f3fd95fc4\"\n"
          + "  ]\n"
          + "}";

  public static final String UNREADABLE_REQUEST =
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

  public static final String INVALID_REQUEST = "{}";

  public static final String REQUEST_LOG =
      "[{\n"
          + "  \"requestId\" : \"92693768-9fdd-494f-b79b-778d64e4ec2f\",\n"
          + "  \"contentType\" : \"application/json\",\n"
          + "  \"queryString\" : \"NoQueryString\",\n"
          + "  \"method\" : \"POST\",\n"
          + "  \"remoteAddress\" : \"0:0:0:0:0:0:0:1\",\n"
          + "  \"requestUri\" : \"/api/farmers\",\n"
          + "  \"requestUrl\" : \"http://localhost:8887/api/farmers\",\n"
          + "  \"requestDateTime\" : \"27-12-2019 @ 13:42:56 pm\",\n"
          + "  \"runningTime\" : 142,\n"
          + "  \"httpStatus\" : 400\n"
          + "}, {\n"
          + "  \"requestId\" : \"943d1664-19c2-4fcf-ae73-b53eed31c9c5\",\n"
          + "  \"contentType\" : null,\n"
          + "  \"queryString\" : \"NoQueryString\",\n"
          + "  \"method\" : \"GET\",\n"
          + "  \"remoteAddress\" : \"0:0:0:0:0:0:0:1\",\n"
          + "  \"requestUri\" : \"/api/farmers/975eebdd-b9fa-493b-ac55-273383b02c86\",\n"
          + "  \"requestUrl\" : \"http://localhost:8887/api/farmers/975eebdd-b9fa-493b-ac55-273383b02c86\",\n"
          + "  \"requestDateTime\" : \"27-12-2019 @ 13:43:20 pm\",\n"
          + "  \"runningTime\" : 80,\n"
          + "  \"httpStatus\" : 200\n"
          + "} ]";
}
