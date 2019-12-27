package com.rubiconwater.codingchallenge.joshluisaac;

import java.util.*;

public class FakeData implements AbstractTest {

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
