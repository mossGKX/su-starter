package com.cntest.su.cache.redisson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cntest.su.cache.AbstractCacheSettings;
import com.cntest.su.cache.CacheRegion;

/**
 * 自定义RedissonCacheManager。从AbstractCacheSettings组件实例中获取缓存配置。
 */
public class GenericRedissonCacheManager extends RedissonSpringCacheManager
    implements ApplicationContextAware {
  private List<AbstractCacheSettings> settings = new ArrayList<>();

  public GenericRedissonCacheManager(RedissonClient redisson) {
    super(redisson);
  }

  @Override
  public void setApplicationContext(ApplicationContext context) {
    Map<String, AbstractCacheSettings> freemarkerSettingsMap =
        context.getBeansOfType(AbstractCacheSettings.class);
    settings.addAll(freemarkerSettingsMap.values());
  }

  @Override
  public void afterPropertiesSet() {
    List<CacheRegion> regions = new ArrayList<>();
    for (AbstractCacheSettings setting : settings) {
      regions.addAll(setting.getRegions());
    }
    Map<String, CacheConfig> cacheConfigMap = new HashMap<>();
    for (CacheRegion region : regions) {
      CacheConfig cacheConfig = createRedisCacheConfiguration(region);
      cacheConfigMap.put(region.getName(), cacheConfig);
    }
    setConfig(cacheConfigMap);
  }

  /**
   * 创建Redisson缓存配置。
   * 
   * @param cacheRegion 缓存分区配置
   * @return 返回Redisson缓存配置。
   */
  private CacheConfig createRedisCacheConfiguration(CacheRegion cacheRegion) {
    CacheConfig cacheConfig = new CacheConfig();
    cacheConfig.setTTL(cacheRegion.getTtl() * 1000L);
    cacheConfig.setMaxIdleTime(cacheRegion.getMaxIdleTime() * 1000L);
    cacheConfig.setMaxSize(cacheRegion.getMaxSize());
    return cacheConfig;
  }
}
