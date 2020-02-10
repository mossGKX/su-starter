package com.cntest.su.cache.redis.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.cntest.su.cache.redis.GenericRedisCacheManager;

@Configuration
@EnableCaching
@EnableRedisHttpSession
@EnableConfigurationProperties(RedisCacheProperties.class)
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {
  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  @Autowired
  private RedisCacheProperties cacheProperties;

  @Bean
  @ConditionalOnProperty(name = "su.cache.redis.x-auth-token", havingValue = "true",
      matchIfMissing = true)
  HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

  @Bean("springSessionDefaultRedisSerializer")
  public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    if ("jackson".equals(cacheProperties.getDefaultSessionSerializer())) {
      return new GenericJackson2JsonRedisSerializer();
    }
    return new JdkSerializationRedisSerializer();
  }

  @Bean
  public RedisTemplate<Object, Object> redisTemplate() {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }

  @Bean
  public ConfigureRedisAction configureRedisAction() {
    return ConfigureRedisAction.NO_OP;
  }

  @Bean
  @Override
  public CacheManager cacheManager() {
    RedisCacheWriter redisCacheWriter =
        RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(cacheProperties.getTtl()))
        .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(
            SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    if (!cacheProperties.getCachingNullValues()) {
      defaultCacheConfig = defaultCacheConfig.disableCachingNullValues();
    }

    return new GenericRedisCacheManager(redisCacheWriter, defaultCacheConfig);
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return (target, method, objects) -> {
      StringBuilder key = new StringBuilder();
      key.append(target.getClass().getName());
      key.append("::" + method.getName() + ":");
      for (Object obj : objects) {
        key.append(obj.toString());
      }
      return key.toString();
    };
  }

  @Bean
  @Override
  public CacheResolver cacheResolver() {
    return new SimpleCacheResolver(cacheManager());
  }

  @Bean
  @Override
  public CacheErrorHandler errorHandler() {
    return new SimpleCacheErrorHandler();
  }
}
