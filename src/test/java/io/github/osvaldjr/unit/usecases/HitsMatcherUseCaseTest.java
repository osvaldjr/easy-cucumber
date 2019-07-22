package io.github.osvaldjr.unit.usecases;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.mock.MockGateway;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.HitsMatcherUseCase;

class HitsMatcherUseCaseTest extends UnitTest {

  @Mock private MockGateway mockGateway;
  @InjectMocks private HitsMatcherUseCase hitsMatcherUsecase;

  @Test
  void shouldExecute(@Random String id) {
    Integer hits = 10;
    when(mockGateway.getMockHits(id)).thenReturn(hits);

    boolean match = hitsMatcherUsecase.execute(id, hits);

    Assertions.assertTrue(match);
    verify(mockGateway, times(1)).getMockHits(anyString());
  }
}
