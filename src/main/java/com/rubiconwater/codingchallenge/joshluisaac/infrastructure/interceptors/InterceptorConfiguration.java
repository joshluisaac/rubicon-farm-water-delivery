package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

  private RequestInterceptor requestInterceptor;

  @Autowired
  public InterceptorConfiguration(RequestInterceptor requestInterceptor) {
    this.requestInterceptor = requestInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(requestInterceptor)
        .addPathPatterns(List.of("/api/farmers/**", "/api/farmers"));
  }
}
