package com.cntest.su.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 资源工具类。
 */
public class ResourceUtils {
  private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

  /**
   * 根据通配符资源路径获取资源列表。
   * 
   * @param wildcardResourcePaths 通配符资源路径
   * @return 返回资源列表。
   */
  public static Resource[] getResourcesByWildcard(String... wildcardResourcePaths) {
    List<Resource> resources = new ArrayList<>();
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    for (String basename : wildcardResourcePaths) {
      try {
        for (Resource resource : resourcePatternResolver.getResources(basename)) {
          resources.add(resource);
        }
      } catch (IOException e) {
        log.warn("没有找到指定的资源路径：{}", basename);
      }
    }
    return resources.toArray(new Resource[resources.size()]);
  }

  private ResourceUtils() {}
}
