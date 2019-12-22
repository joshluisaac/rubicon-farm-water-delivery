package com.rubiconwater.codingchallenge.joshluisaac.businessactivities;

public class Routes {

  public static class Farmers {
    public static final String FARMERS = "/api/farmers";
    public static final String FARMER = "/api/farmers/{farmId}";
  }

  // api/farmers/:farm_id/orders/:request_order_id?cancel=true

  // api/farmers/:farm_id/orders/:request_order_id?cancel=true

  public static class Requests {
    public static final String REQUESTS_AS_JSON = "/api/requests";
    public static final String REQUEST_AS_JSON = "/api/requests/{id}";
    public static final String REQUESTS = "/api/requests";
  }
}
