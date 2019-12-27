package com.rubiconwater.codingchallenge.joshluisaac.businessactivities.requestmonitoring;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors.RequestAction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping(value = "/api/requests")
  @ResponseBody
  public ResponseEntity<List<RequestAction>> getRequestsAsJson() {
    return new ResponseEntity<>(requestAuditService.getRequests(), HttpStatus.OK);
  }

  @GetMapping(value = "/api/requests/{id}")
  @ResponseBody
  public ResponseEntity<RequestAction> getRequestAsJson(@PathVariable String id) {
    return new ResponseEntity<>(requestAuditService.getRequest(id), HttpStatus.OK);
  }

  @GetMapping(value = "requests")
  public String apiRequests(Model model) {
    model.addAttribute("auditList", requestAuditService.getRequests());
    return "audit";
  }
}
