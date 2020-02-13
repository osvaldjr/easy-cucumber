package io.github.osvaldjr.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

  @Value("${spring.datasource.url:}")
  private String url;

  @Value("${spring.datasource.username:}")
  private String username;

  @Value("${spring.datasource.password:}")
  private String password;

  @Value("${spring.jpa.properties.hibernate.dialect:org.hibernate.dialect.PostgreSQLDialect}")
  private String hibernateDialect;

  @Bean(name = "easyCucumberDataSource")
  @ConditionalOnProperty("spring.datasource.url")
  public DataSource dataSource() {
    return DataSourceBuilder.create().url(url).username(username).password(password).build();
  }

  @Bean(name = "easyCucumberDataSource")
  public DataSource noDBDataSource() {
    return DataSourceBuilder.create().build();
  }
}
