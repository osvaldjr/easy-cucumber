package com.spring5.context;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("component-test")
public class ContextLoader {

  @Before
  public void startContext() {}
}
