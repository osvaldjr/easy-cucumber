package io.github.osvaldjr.datasource.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "easycucumber.datasource.autoconfigure", havingValue = "true")
public class DatabaseConfig {

  @Value("${easycucumber.datasource.url:}")
  private String url;

  @Value("${easycucumber.datasource.username:}")
  private String username;

  @Value("${easycucumber.datasource.password:}")
  private String password;

  @Bean(name = "easyCucumberDataSource")
  public DataSource dataSource() {
    log.info(
        "Easy Cucumber auto configure datasource, url = {}, = username {}, = password {}",
        url,
        username,
        password);
    return DataSourceBuilder.create().url(url).username(username).password(password).build();
  }
}
