package com.spring5.context;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import cucumber.api.java.Before;
import io.github.osvaldjr.core.annotation.EnableEasyCucumber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("component-test")
@EnableEasyCucumber
public class ContextLoader {

  @Before
  public void startContext() {}
}
