package br.community.component.test.unit.gateways;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.community.component.test.gateways.FileGateway;
import br.community.component.test.unit.UnitTest;
import io.github.glytching.junit.extension.random.Random;

public class FileGatewayTest extends UnitTest {

  @Mock ObjectMapper objectMapper;
  @Mock InputStream inputStream;
  @InjectMocks private FileGateway fileGateway;

  @Test
  void shouldGetObjectFromFile(@Random Object object) throws IOException {
    String scenario = "scenario", file = "example";
    when(objectMapper.readValue(any(InputStream.class), eq(Object.class))).thenReturn(object);

    Object response = fileGateway.getObjectFromFile(scenario, file, Object.class);

    assertThat(response, equalTo(object));
    verify(objectMapper, times(1)).readValue(any(InputStream.class), eq(Object.class));
  }

  @Test
  void shouldGetObjectFromFileNotFound() throws IOException {
    String scenario = "scenario", file = "invalid";

    assertThrows(
        FileNotFoundException.class,
        () -> fileGateway.getObjectFromFile(scenario, file, Object.class));

    verify(objectMapper, never()).readValue(any(InputStream.class), eq(Object.class));
  }

  @Test
  void shouldGetJsonStringFromObject(@Random Object response, @Random String responseObject)
      throws JsonProcessingException {
    when(objectMapper.writeValueAsString(response)).thenReturn(responseObject);

    String object = objectMapper.writeValueAsString(response);

    verify(objectMapper, times(1)).writeValueAsString(response);
    assertThat(object, equalTo(responseObject));
  }

  @Test
  void shouldGetJsonStringFromFile(@Random String object) throws IOException {
    String scenario = "scenario", file = "example";
    when(objectMapper.readValue(any(InputStream.class), eq(Object.class))).thenReturn(object);
    when(objectMapper.writeValueAsString(object)).thenReturn(object);

    String stringResponse = fileGateway.getJsonStringFromFile(scenario, file);

    verify(objectMapper, times(1)).readValue(any(InputStream.class), eq(Object.class));
    verify(objectMapper, times(1)).writeValueAsString(object);
    assertThat(stringResponse, equalTo(object));
  }

  @Test
  void shouldGetJsonStringFromFileNotFound() throws IOException {
    String scenario = "scenario", file = "invalid";

    assertThrows(
        FileNotFoundException.class,
        () -> fileGateway.getObjectFromFile(scenario, file, Object.class));

    verify(objectMapper, never()).readValue(any(InputStream.class), eq(Object.class));
  }
}
