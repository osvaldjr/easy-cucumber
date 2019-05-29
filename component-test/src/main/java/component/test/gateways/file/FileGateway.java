package component.test.gateways.file;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FileGateway {

  ObjectMapper objectMapper;
  private static final String DATA_DIRECTORY = "/data/";

  @Autowired
  public FileGateway(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String getJsonStringFromFile(String scenario, String file) throws IOException {
    return getJsonStringFromObject(getObjectFromFile(scenario, file, Object.class));
  }

  public <T> T getObjectFromFile(String scenario, String file, Class<T> clazz) throws IOException {
    InputStream inputStream =
        this.getClass().getResourceAsStream(DATA_DIRECTORY + scenario + "/" + file + ".json");
    return objectMapper.readValue(inputStream, clazz);
  }

  public String getJsonStringFromObject(Object response) throws JsonProcessingException {
    return objectMapper.writeValueAsString(response);
  }
}
