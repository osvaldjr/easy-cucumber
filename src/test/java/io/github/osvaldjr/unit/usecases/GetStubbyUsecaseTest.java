package io.github.osvaldjr.unit.usecases;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponse;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.GetStubbyUsecase;

class GetStubbyUsecaseTest extends UnitTest {

  @Mock private StubbyGateway stubbyGateway;
  @InjectMocks private GetStubbyUsecase getStubbyUsecase;

  @Test
  void shouldExecute(@Random Integer id, @Random StubbyResponse stubbyResponse) {
    when(stubbyGateway.getStubbyResponse(id)).thenReturn(stubbyResponse);

    StubbyResponse stubby = getStubbyUsecase.execute(id);

    assertThat(stubby, equalTo(stubbyResponse));
    verify(stubbyGateway, times(1)).getStubbyResponse(anyInt());
  }
}
