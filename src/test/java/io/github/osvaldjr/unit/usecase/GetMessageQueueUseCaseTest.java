package io.github.osvaldjr.unit.usecase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.activemq.ActiveMQGateway;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecase.GetMessageQueueUseCase;

class GetMessageQueueUseCaseTest extends UnitTest {

  @Mock private ActiveMQGateway activeMQGateway;
  @InjectMocks private GetMessageQueueUseCase getMessageQueueUseCase;

  @Test
  void shouldExecute(@Random String destinationQueue, @Random Object mockMessage) {
    when(activeMQGateway.getMessageQueue(destinationQueue)).thenReturn(mockMessage);

    Object message = getMessageQueueUseCase.execute(destinationQueue);

    assertThat(message, equalTo(mockMessage));
    verify(activeMQGateway, times(1)).getMessageQueue(destinationQueue);
  }
}
