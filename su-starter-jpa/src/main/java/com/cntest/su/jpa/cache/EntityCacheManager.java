package com.cntest.su.jpa.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

/**
 * 实体缓存管理组件。
 */
public class EntityCacheManager {
  @Autowired
  private EntityManagerFactory entityManagerFactory;
  @Getter
  private List<Class<?>> cachedEntityClasses = new ArrayList<>();

  /**
   * 初始化方法。
   */
  @PostConstruct
  public void init() {
    Metamodel metamodel = entityManagerFactory.getMetamodel();
    for (EntityType<?> entry : metamodel.getEntities()) {
      Class<?> entityClass = entry.getJavaType();
      if (entityClass.isAnnotationPresent(Cache.class)) {
        cachedEntityClasses.add(entityClass);
      }
    }
  }

  /**
   * 清空指定实体缓存。
   * 
   * @param entityClasses 实体类列表
   */
  public void evictEntityRegion(Class<?>... entityClasses) {
    for (Class<?> entityClass : entityClasses) {
      getCache().evict(entityClass);
    }
  }

  /**
   * 清空所有实体缓存。
   */
  public void evictEntityRegions() {
    getCache().evictEntityRegions();
  }

  /**
   * 清空所有集合缓存。
   */
  public void evictCollectionRegions() {
    getCache().evictCollectionRegions();
  }

  /**
   * 清空所有查询缓存。
   */
  public void evictQueryRegions() {
    getCache().evictQueryRegions();
  }

  /**
   * 清空所有缓存。
   */
  public void evictAllRegions() {
    getCache().evictAll();
  }

  private org.hibernate.Cache getCache() {
    return entityManagerFactory.unwrap(SessionFactory.class).getCache();
  }
}
