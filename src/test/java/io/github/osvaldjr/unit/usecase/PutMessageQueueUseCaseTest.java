package io.github.osvaldjr.unit.usecase;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.FileGateway;
import io.github.osvaldjr.gateways.activemq.ActiveMQGateway;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecase.PutMessageQueueUseCase;

class PutMessageQueueUseCaseTest extends UnitTest {

  @Mock private ActiveMQGateway activeMQGateway;
  @Mock private FileGateway fileGateway;
  @InjectMocks private PutMessageQueueUseCase putMessageQueueUseCase;

  @Test
  void shouldExecute(
      @Random String scenario,
      @Random String destinationQueue,
      @Random String file,
      @Random Object message)
      throws FileNotFoundException {
    when(fileGateway.getObjectFromFile(scenario, "messages/" + file, Object.class))
        .thenReturn(message);

    putMessageQueueUseCase.execute(scenario, destinationQueue, file);

    verify(fileGateway, times(1)).getObjectFromFile(anyString(), anyString(), any());
    verify(activeMQGateway, times(1)).putMessageQueue(destinationQueue, message);
  }
}
