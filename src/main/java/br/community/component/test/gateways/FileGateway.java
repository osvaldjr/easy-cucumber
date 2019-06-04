package br.community.component.test.gateways;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FileGateway {

  ObjectMapper objectMapper;
  private static final String DATA_DIRECTORY = "/data";

  @Autowired
  public FileGateway(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String getJsonStringFromFile(String scenario, String file)
      throws FileNotFoundException, JsonProcessingException {
    return getJsonStringFromObject(getObjectFromFile(scenario, file, Object.class));
  }

  public <T> T getObjectFromFile(String scenario, String file, Class<T> clazz)
      throws FileNotFoundException {
    String filePath = MessageFormat.format("{0}/{1}/{2}.json", DATA_DIRECTORY, scenario, file);
    try {
      InputStream inputStream = getClass().getResourceAsStream(filePath);
      T object = objectMapper.readValue(inputStream, clazz);
      inputStream.close();
      return object;
    } catch (IOException e) {
      throw new FileNotFoundException(
          "File ["
              + filePath
              + "] not found. \n Check if your 'resources/data/<YOUR_FEATURE_NAME>/<YOUR FILE>.json' exists");
    }
  }

  public String getJsonStringFromObject(Object response) throws JsonProcessingException {
    return objectMapper.writeValueAsString(response);
  }
}
