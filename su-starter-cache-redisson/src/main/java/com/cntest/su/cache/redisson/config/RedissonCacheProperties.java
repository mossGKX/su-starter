package com.cntest.su.cache.redisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 缓存配置。
 */
@Data
@ConfigurationProperties("su.cache.redisson")
public class RedissonCacheProperties {
  /** 是否打开x-auth-token */
  private Boolean xAuthToken = true;
  /** redisson配置文件 */
  private String configFile = "classpath:redisson.yml";
  /** 默认序列化方式 */
  private String defaultSerializer = "jackson";
}
