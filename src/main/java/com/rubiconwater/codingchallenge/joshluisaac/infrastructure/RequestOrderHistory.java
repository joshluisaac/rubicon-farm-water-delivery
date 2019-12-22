package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestOrderHistory {

  Map<String, List<WaterDeliveryOrder>> requestHistory = new HashMap<>();
}
