package io.github.osvaldjr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(
    exclude = {
      ActiveMQAutoConfiguration.class,
      DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class
    })
public class ApplicationConfiguration {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfiguration.class, args);
  }
}
