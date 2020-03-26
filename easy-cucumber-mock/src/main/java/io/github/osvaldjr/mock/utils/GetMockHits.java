package io.github.osvaldjr.mock.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetMockHits {

  private final Mock mock;

  @Autowired
  public GetMockHits(Mock mock) {
    this.mock = mock;
  }

  public <T> Integer execute(T id) {
    return mock.getMockHits(id);
  }
}
