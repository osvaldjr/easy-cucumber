package io.github.osvaldjr.unit.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.activemq.ActiveMQGateway;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecase.CleanQueueUseCase;

class CleanQueueUseCaseTest extends UnitTest {

  @Mock private ActiveMQGateway activeMQGateway;
  @InjectMocks private CleanQueueUseCase cleanQueueUseCase;

  @Test
  void shouldExecute(@Random String destinationQueue) {
    cleanQueueUseCase.execute(destinationQueue);

    verify(activeMQGateway, times(1)).cleanQueue(destinationQueue);
  }
}
