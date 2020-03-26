package example.simple.springboot.datasource.steps;

import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.Before;
import example.simple.springboot.datasource.SimpleSpringbootDatasourceApplication;
import io.github.osvaldjr.core.EasyCucumberContextLoader;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    classes = {
      SimpleSpringbootDatasourceApplication.class,
      EasyCucumberContextLoader.class,
    })
public class ContextLoader {

  @Before
  public void setUp() {
    // Empty method, just to setup Application Test Context
  }
}
