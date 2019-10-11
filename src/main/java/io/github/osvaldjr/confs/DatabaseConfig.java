package io.github.osvaldjr.confs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

// import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@EnableAutoConfiguration(
    exclude = {
      DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class
    })
// #spring.autoconfigure.exclude:
// org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,
// org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,
// org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
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
  @ConditionalOnMissingBean(DataSource.class)
  public DataSource dataSource() {
    return DataSourceBuilder.create().url(url).username(username).password(password).build();
  }

  @Bean
  @Qualifier("easyCucumberDataSource")
  @ConditionalOnBean(name = "easyCucumberDataSource")
  public JdbcTemplate easyCucumberJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
