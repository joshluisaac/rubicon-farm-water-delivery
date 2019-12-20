package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.requestmonitoring;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestAction;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestObserver;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestAuditService {

  private RequestObserver observer;

  @Autowired
  public RequestAuditService(RequestObserver observer) {
    this.observer = observer;
  }

  public List<RequestAction> getRequests() {
    return observer.getRequestActions();
  }

  public RequestAction getRequest(String requestId) {
    return observer
        .getRequestActions()
        .stream()
        .filter(entry -> entry.getRequestId().equals(UuidUtils.toUuid(requestId)))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(String.format("Request (%s) not found", requestId)));
  }
}
