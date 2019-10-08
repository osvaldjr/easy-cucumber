package io.github.osvaldjr.confs;

import org.ff4j.FF4j;
import org.ff4j.conf.XmlConfig;
import org.ff4j.redis.RedisConnection;
import org.ff4j.store.EventRepositoryRedis;
import org.ff4j.store.FeatureStoreRedis;
import org.ff4j.store.PropertyStoreRedis;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(FF4j.class)
public class FF4jConfig {

  @Value("${dependencies.ff4j.redis.server:#{null}}")
  private String redisServer;

  @Value("${dependencies.ff4j.redis.port:#{null}}")
  private Integer redisPort;

  @Value("${dependencies.ff4j.test:false}")
  private boolean isTest;

  @Bean(name = "easyCucumberFF4J")
  @Qualifier("easyCucumberFF4J")
  public FF4j getEasyCucumberFF4j() {
    FF4j ff4j = new FF4j();

    if (redisServer != null && redisPort != null) {
      RedisConnection redisConnection = new RedisConnection(redisServer, redisPort);

      ff4j.setFeatureStore(new FeatureStoreRedis(redisConnection));
      ff4j.setPropertiesStore(new PropertyStoreRedis(redisConnection));
      ff4j.setEventRepository(new EventRepositoryRedis(redisConnection));
    }

    if (isTest) {
      ff4j.autoCreate();

      XmlConfig xmlConfig = ff4j.parseXmlConfig("ff4j-features.xml");
      ff4j.getFeatureStore().importFeatures(xmlConfig.getFeatures().values());
      ff4j.getPropertiesStore().importProperties(xmlConfig.getProperties().values());
    }

    return ff4j;
  }
}
