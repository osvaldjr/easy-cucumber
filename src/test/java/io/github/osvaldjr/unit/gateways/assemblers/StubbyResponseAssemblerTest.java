package io.github.osvaldjr.unit.gateways.assemblers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.StubbyResponse;
import io.github.osvaldjr.gateways.stubby.assemblers.StubbyResponseAssembler;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonResponse;
import io.github.osvaldjr.unit.UnitTest;

class StubbyResponseAssemblerTest extends UnitTest {

  @InjectMocks private StubbyResponseAssembler stubbyResponseAssembler;

  @Test
  void shouldAssembleResponseCorrectly(@Random StubbyJsonResponse stubbyJsonResponse) {
    StubbyResponse stubbyResponse = stubbyResponseAssembler.assemble(stubbyJsonResponse);

    assertThat(stubbyResponse, notNullValue());
    assertThat(stubbyResponse.getHits(), equalTo(stubbyJsonResponse.getHits()));
    assertThat(stubbyResponse.getId(), equalTo(stubbyJsonResponse.getId()));
  }
}
