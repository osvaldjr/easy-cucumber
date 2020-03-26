package example.complete.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"example.complete.springboot.client"})
public class CompleteSpringbootApplication {

  public static void main(String[] args) {
    SpringApplication.run(CompleteSpringbootApplication.class, args);
  }
}
