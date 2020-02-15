package io.github.osvaldjr.databases.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("spring.datasource.url")
public class DatabaseConfig {

  @Value("${spring.datasource.url:}")
  private String url;

  @Value("${spring.datasource.username:}")
  private String username;

  @Value("${spring.datasource.password:}")
  private String password;

  @Bean(name = "easyCucumberDataSource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().url(url).username(username).password(password).build();
  }
}
