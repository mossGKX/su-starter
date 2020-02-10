package com.cntest.su.cache;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Cache配置抽象类。
 */
public abstract class AbstractCacheSettings {
  @Getter
  private List<CacheRegion> regions = new ArrayList<>();

  /**
   * 添加Cache配置（全部使用默认值）。
   * 
   * @param regionNames Cache名称
   */
  public void addRegion(String... regionNames) {
    for (String regionName : regionNames) {
      addRegion(new CacheRegion(regionName));
    }
  }

  /**
   * 添加Cache配置。
   * 
   * @param cacheRegions Cache配置
   */
  public void addRegion(CacheRegion... cacheRegions) {
    for (CacheRegion cacheRegion : cacheRegions) {
      regions.add(cacheRegion);
    }
  }
}
