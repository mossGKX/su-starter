package com.cntest.su.jpa.config;

import com.cntest.su.cache.AbstractCacheSettings;

/**
 * 查询缓存配置。
 */
public class CacheSettings extends AbstractCacheSettings {
  public CacheSettings() {
    addRegion("org.hibernate.cache.spi.UpdateTimestampsCache",
        "org.hibernate.cache.internal.StandardQueryCache");
  }
}
