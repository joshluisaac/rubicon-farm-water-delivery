package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** This is a boundary object that communicates with the external world. */
@Controller
public class WaterDeliveryViewHandler {

  @GetMapping(value = "/index")
  @ResponseBody
  public String myIndex() {
    return "My index page";
  }

  @GetMapping(value = "/")
  @ResponseBody
  public String myIndex2() {
    return "My index page";
  }

  public void cancelOrder() {}

  public void updateOrder() {}

  public void deleteOrder() {}

  public void closeOrder() {}

  public void dispatchOrder() {}
}
