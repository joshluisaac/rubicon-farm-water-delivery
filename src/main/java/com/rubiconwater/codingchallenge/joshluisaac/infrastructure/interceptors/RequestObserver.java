package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.FileUtils;
import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.JsonMappers;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component(value = "RequestObserverComponent")
public class RequestObserver {

  private static final Logger LOG = LoggerFactory.getLogger(RequestObserver.class);

  @Value("file:data/RequestLog.json")
  private Resource resource;

  private List<RequestAction> requestActions = new ArrayList<>();

  @PostConstruct
  public void load() throws IOException {
    InputStream inputStream = resource.getInputStream();
    requestActions =
        JsonMappers.buildReader()
            .forType(new TypeReference<List<RequestAction>>() {})
            .readValue(inputStream);
    LOG.info(
        "Loaded and initialized request dataset from  {}", resource.getFile().getAbsolutePath());
  }

  void update(RequestAction action) throws IOException {
    requestActions.add(action);
    writeToDisk();
  }

  private void writeToDisk() throws IOException {
    FileUtils.flushToDisk(serializeRequest(), resource.getFile());
  }

  private String serializeRequest() throws IOException {
    return JsonMappers.buildWriter()
        .forType(new TypeReference<List<RequestAction>>() {})
        .writeValueAsString(requestActions);
  }

  public List<RequestAction> getRequestActions() {
    return requestActions;
  }
}
