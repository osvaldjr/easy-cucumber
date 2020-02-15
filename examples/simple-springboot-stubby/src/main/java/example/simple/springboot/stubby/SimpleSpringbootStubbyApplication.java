package example.simple.springboot.stubby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"example.simple.springboot.stubby.client"})
public class SimpleSpringbootStubbyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleSpringbootStubbyApplication.class, args);
  }
}
