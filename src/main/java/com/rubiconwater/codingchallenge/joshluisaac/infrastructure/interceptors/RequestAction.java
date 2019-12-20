package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors;

import java.beans.ConstructorProperties;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RequestAction {

  private UUID requestId;
  private String contentType;
  private String queryString;
  private String method;
  private String remoteAddress;
  private String requestUri;
  private String requestUrl;
  private String requestDateTime;
  private long runningTime;

  @ConstructorProperties({
    "requestId",
    "contentType",
    "queryString",
    "method",
    "remoteAddress",
    "requestUri",
    "requestUrl",
    "requestDateTime",
    "runningTime"
  })
  public RequestAction(
      UUID requestId,
      String contentType,
      String queryString,
      String method,
      String remoteAddress,
      String requestUri,
      String requestUrl,
      String requestDateTime,
      long runningTime) {
    this.requestId = requestId;
    this.contentType = contentType;
    this.queryString = queryString;
    this.method = method;
    this.remoteAddress = remoteAddress;
    this.requestUri = requestUri;
    this.requestUrl = requestUrl;
    this.requestDateTime = requestDateTime;
    this.runningTime = runningTime;
  }
}
