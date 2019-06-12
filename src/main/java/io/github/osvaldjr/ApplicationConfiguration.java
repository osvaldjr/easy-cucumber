package io.github.osvaldjr;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class ApplicationConfiguration {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfiguration.class, args);
  }
}
