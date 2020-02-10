package com.cntest.su.cache.redisson.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.config.Config;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.cntest.su.cache.redisson.GenericRedissonCacheManager;

@Configuration
@EnableCaching
@EnableRedissonHttpSession
@EnableConfigurationProperties(RedissonCacheProperties.class)
public class RedissonCacheAutoConfiguration {
  @Autowired
  private RedissonCacheProperties cacheProperties;

  @Bean
  @ConditionalOnProperty(name = "su.cache.redisson.x-auth-token", havingValue = "true",
      matchIfMissing = true)
  HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

  @Bean(destroyMethod = "shutdown")
  RedissonClient redisson() throws IOException {
    String configFile = cacheProperties.getConfigFile();
    ClassPathResource resource = new ClassPathResource(configFile);
    Config config = null;
    if (isJsonFile(configFile)) {
      config = Config.fromJSON(resource.getInputStream());
    } else {
      config = Config.fromYAML(resource.getInputStream());
    }
    if ("fst".equals(cacheProperties.getDefaultSerializer())) {
      config.setCodec(new FstCodec());
    }
    return Redisson.create(config);
  }

  @Bean
  CacheManager cacheManager(RedissonClient redissonClient) {
    return new GenericRedissonCacheManager(redissonClient);
  }

  private Boolean isJsonFile(String fileName) {
    return fileName.endsWith(".json");
  }
}
