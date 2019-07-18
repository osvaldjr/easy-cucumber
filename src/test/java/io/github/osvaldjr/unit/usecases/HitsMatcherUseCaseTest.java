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
import io.github.osvaldjr.domains.StubbyResponse;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.HitsMatcherUseCase;

class HitsMatcherUseCaseTest extends UnitTest {

  @Mock private StubbyGateway stubbyGateway;
  @InjectMocks private HitsMatcherUseCase hitsMatcherUsecase;

  @Test
  void shouldExecute(@Random String id, @Random StubbyResponse stubbyResponse) {
    stubbyResponse.setHits(10);
    when(stubbyGateway.getStubbyResponse(id)).thenReturn(stubbyResponse);

    boolean match = hitsMatcherUsecase.execute(id, 10);

    Assertions.assertTrue(match);
    verify(stubbyGateway, times(1)).getStubbyResponse(anyString());
  }
}
