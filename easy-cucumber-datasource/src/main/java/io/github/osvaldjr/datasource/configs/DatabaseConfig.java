package io.github.osvaldjr.datasource.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.osvaldjr.core.objects.properties.ApplicationProperties;
import io.github.osvaldjr.core.objects.properties.DatasourceProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "easycucumber.dependencies.datasource.autoconfigure", havingValue = "true")
public class DatabaseConfig {

  private final ApplicationProperties applicationProperties;

  @Autowired
  public DatabaseConfig(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Bean(name = "easyCucumberDataSource")
  public DataSource dataSource() {
    DatasourceProperties datasourceProperties =
        applicationProperties.getDependencies().getDatasource();
    log.info(
        "Easy Cucumber auto configure datasource, url = {}, = username {}, = password {}",
        datasourceProperties.getUrl(),
        datasourceProperties.getUsername(),
        datasourceProperties.getPassword());

    return DataSourceBuilder.create()
        .url(datasourceProperties.getUrl())
        .username(datasourceProperties.getUsername())
        .password(datasourceProperties.getPassword())
        .build();
  }
}
