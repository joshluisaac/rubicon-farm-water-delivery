package com.rubiconwater.codingchallenge.joshluisaac.infrastructure;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.WaterDeliveryRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/** The responsibility of this class is to store delivery requests. */
@Service(value = "DataBaseService")
public class DataStore {

  // contains requests which are either in REQUESTED or IN_PROGRESS states
  Map<String, List<WaterDeliveryRequest>> activeDeliveryRequests = new HashMap<>();
}
