package io.github.osvaldjr.confs;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration(
    exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "easyCucumberEntityManagerFactory",
    transactionManagerRef = "easyCucumberTransactionManager",
    basePackages = {"io.github.osvaldjr"})
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

  @Bean(name = "easyCucumberEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("easyCucumberDataSource") DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("io.github.osvaldjr");

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());
    return em;
  }

  @Bean(name = "easyCucumberTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("easyCucumberEntityManagerFactory") EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);

    return transactionManager;
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", hibernateDialect);
    properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
    return properties;
  }
}
