package br.community.component.test.confs;

import org.ff4j.FF4j;
import org.ff4j.redis.RedisConnection;
import org.ff4j.store.EventRepositoryRedis;
import org.ff4j.store.FeatureStoreRedis;
import org.ff4j.store.PropertyStoreRedis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4jConfig {

  @Value("${dependencies.ff4j.redis.server}")
  private String redisServer;

  @Value("${dependencies.ff4j.redis.port}")
  private Integer redisPort;

  @Bean
  public FF4j configureFF4j() {
    FF4j ff4j = new FF4j();
    RedisConnection redisConnection = new RedisConnection(redisServer, redisPort);
    ff4j.setFeatureStore(new FeatureStoreRedis(redisConnection));
    ff4j.setPropertiesStore(new PropertyStoreRedis(redisConnection));
    ff4j.setEventRepository(new EventRepositoryRedis(redisConnection));
    return ff4j;
  }
}
