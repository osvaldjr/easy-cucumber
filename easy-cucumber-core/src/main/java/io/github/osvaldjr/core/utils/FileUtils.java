package io.github.osvaldjr.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;

import static java.text.MessageFormat.format;

@Component
public class FileUtils {

  private static final String DATA_DIRECTORY = "/data";
  ObjectMapper objectMapper;

  @Autowired
  public FileUtils(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String getJsonStringFromFile(String scenario, String file)
      throws FileNotFoundException, JsonProcessingException {
    return getJsonStringFromObject(getObjectFromFile(scenario, file, Object.class));
  }

  public <T> T getObjectFromFile(String scenario, String file, Class<T> clazz)
      throws FileNotFoundException {
    String filePath = format("{0}/{1}/{2}", DATA_DIRECTORY, scenario, file);
    try (InputStream inputStream =
        new FileInputStream(
            new ClassPathResource(filePath, getClass().getClassLoader()).getFile())) {
      return objectMapper.readValue(inputStream, clazz);
    } catch (IOException e) {
      throw new FileNotFoundException(
          "File ["
              + filePath
              + "] not found. \n Check if your 'resources/data/<YOUR_FEATURE_NAME>/<YOUR FILE>' exists");
    }
  }

  public String getJsonStringFromObject(Object response) throws JsonProcessingException {
    return objectMapper.writeValueAsString(response);
  }

  public String getFileContent(String path) throws IOException {
    String filePath = format("{0}/{1}", DATA_DIRECTORY, path);

    try (InputStream inputStream =
        new FileInputStream(
            new ClassPathResource(filePath, getClass().getClassLoader()).getFile())) {
      String text;
      try (Reader reader = new InputStreamReader(inputStream)) {
        text = CharStreams.toString(reader);
      }

      return text;
    } catch (IOException e) {
      throw new FileNotFoundException("File [" + filePath + "] not found.");
    }
  }
}
