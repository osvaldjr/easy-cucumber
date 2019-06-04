package br.community.component.test.usecases;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.community.component.test.confs.UnitTest;
import br.community.component.test.gateways.stubby.StubbyGateway;
import br.community.component.test.gateways.stubby.jsons.StubbyResponse;
import io.github.glytching.junit.extension.random.Random;

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
