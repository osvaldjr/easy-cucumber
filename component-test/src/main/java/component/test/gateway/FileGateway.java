package component.test.gateway;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FileGateway {

  ObjectMapper objectMapper;

  @Autowired
  public FileGateway(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String getJsonStringFromFile(String scenario, String file) throws IOException {
    return getJsonStringFromObject(getObjectFromFile(scenario, file));
  }

  public Object getObjectFromFile(String scenario, String file) throws IOException {
    InputStream inputStream =
        this.getClass().getResourceAsStream("/scenarios/" + scenario + "/" + file + ".json");
    return objectMapper.readValue(inputStream, Object.class);
  }

  public String getJsonStringFromObject(Object response) throws JsonProcessingException {
    return objectMapper.writeValueAsString(response);
  }
}
