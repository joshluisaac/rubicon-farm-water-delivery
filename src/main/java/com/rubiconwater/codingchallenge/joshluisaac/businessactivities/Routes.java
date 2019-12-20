package com.rubiconwater.codingchallenge.joshluisaac.businessactivities;

public class Routes {

  public static class Farmers {
    public static final String FARMERS_ALL = "/api/farmers";
    public static final String FARMER_BY_ID = "/api/farmers/{id}";
  }

  public static class Requests {
    public static final String REQUESTS_AS_JSON = "/api/requests";
    public static final String REQUEST_AS_JSON = "/api/requests/{id}";
    public static final String REQUESTS = "/api/requests";
  }
}
