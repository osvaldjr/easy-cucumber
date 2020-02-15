package example.simple.springboot.mockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"example.simple.springboot.mockserver.client"})
public class SimpleSpringbootMockserverApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleSpringbootMockserverApplication.class, args);
  }
}
