package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.interceptors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
public class RequestObserverTest {

  @Mock private Resource resource;

  @InjectMocks private RequestObserver requestObserver;

  @BeforeEach
  public void beforeRequestObserverTest() throws IOException {
    InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream("TestRequestLog.json");
    when(resource.getInputStream()).thenReturn(inputStream);
    when(resource.getFile()).thenReturn(new File("TestRequestLog.json"));
    requestObserver.load();
  }

  @Test
  public void shouldFetch_ItemsIn_RequestLog() {
    assertThat(requestObserver.getRequestActions().size()).isEqualTo(4);
  }

  @Test
  public void shouldWrite_RequestToLogFile() throws IOException {
    RequestAction requestAction = RequestAction.builder().build();
    requestObserver.update(requestAction);
    assertThat(requestObserver.getRequestActions().size()).isEqualTo(5);
  }
}
