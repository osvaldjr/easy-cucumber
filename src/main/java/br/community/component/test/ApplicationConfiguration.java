package br.community.component.test;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class ApplicationConfiguration {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfiguration.class, args);
  }
}
