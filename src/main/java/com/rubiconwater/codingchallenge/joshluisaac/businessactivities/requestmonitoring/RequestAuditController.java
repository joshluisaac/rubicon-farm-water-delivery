package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.requestmonitoring;

import static com.rubiconwater.codingchallenge.joshluisaac.businessactivities.Routes.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestAction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestAuditController {

  private RequestAuditService requestAuditService;

  @Autowired
  public RequestAuditController(RequestAuditService requestAuditService) {
    this.requestAuditService = requestAuditService;
  }

  @GetMapping(value = Requests.REQUESTS_AS_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String getRequestsAsJson() throws JsonProcessingException {
    return JsonMappers.buildWriter()
        .forType(new TypeReference<List<RequestAction>>() {})
        .writeValueAsString(requestAuditService.getRequests());
  }

  @GetMapping(value = Requests.REQUEST_AS_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String getRequestAsJson(@PathVariable String id) throws JsonProcessingException {
    return JsonMappers.buildWriter()
        .forType(new TypeReference<RequestAction>() {})
        .writeValueAsString(requestAuditService.getRequest(id));
  }

  @GetMapping(value = Requests.REQUESTS)
  public String apiRequests(Model model) {
    model.addAttribute("auditList", requestAuditService.getRequests());
    return "audit";
  }
}
