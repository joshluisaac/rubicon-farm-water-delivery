package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors;

import com.rubiconwater.codingchallenge.joshluisaac.infrastructure.common.UuidUtils;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public final class RequestInterceptor extends HandlerInterceptorAdapter {

  private RequestObserver requestObserver;

  @Autowired
  public RequestInterceptor(RequestObserver requestObserver) {
    this.requestObserver = requestObserver;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    request.setAttribute("accessedDateTime", LocalDateTime.now());
    buildRequestAction(request);
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable ModelAndView modelAndView)
      throws Exception {}

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    RequestAction requestAction = createRequestAction(request, response);
    notifyRequestObserver(requestAction);
  }

  private void buildRequestAction(HttpServletRequest request) {
    RequestAction.RequestActionBuilder requestActionBuilder =
        RequestAction.builder()
            .requestId(UuidUtils.create())
            .requestDateTime(
                LocalDateTime.now()
                    .format(
                        DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy @ HH:mm:ss a",
                            new Locale.Builder().setLanguage("en").setRegion("AU").build())))
            .method(request.getMethod())
            .contentType(request.getContentType())
            .queryString(
                (request.getQueryString() != null) ? request.getQueryString() : "NoQueryString")
            .remoteAddress(request.getRemoteAddr())
            .requestUri(request.getRequestURI())
            .requestUrl(request.getRequestURL().toString());
    request.setAttribute("RequestAction", requestActionBuilder);
  }

  private RequestAction createRequestAction(
      HttpServletRequest request, HttpServletResponse response) {
    long requestResponseDuration =
        Duration.between(
                (LocalDateTime) request.getAttribute("accessedDateTime"), LocalDateTime.now())
            .toMillis();
    RequestAction.RequestActionBuilder requestActionBuilder =
        (RequestAction.RequestActionBuilder) request.getAttribute("RequestAction");
    return requestActionBuilder
        .runningTime(requestResponseDuration)
        .httpStatus(response.getStatus())
        .build();
  }

  private void notifyRequestObserver(RequestAction action) throws IOException {
    requestObserver.update(action);
  }
}
