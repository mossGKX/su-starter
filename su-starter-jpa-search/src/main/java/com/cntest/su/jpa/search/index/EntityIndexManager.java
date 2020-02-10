package com.cntest.su.jpa.search.index;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.CacheMode;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.exception.SysException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 实体全文索引管理组件。
 */
@Slf4j
public class EntityIndexManager {
  @Autowired
  private EntityManagerFactory entityManagerFactory;
  @Getter
  private List<Class<?>> indexedEntityClasses = new ArrayList<>();

  /**
   * 初始化方法。
   */
  @PostConstruct
  public void init() {
    Metamodel metamodel = entityManagerFactory.getMetamodel();
    for (EntityType<?> entry : metamodel.getEntities()) {
      Class<?> entityClass = entry.getJavaType();
      if (entityClass.isAnnotationPresent(Indexed.class)) {
        indexedEntityClasses.add(entityClass);
      }
    }
  }

  /**
   * 同步创建指定实体类的全文索引，如果未指定实体类则创建全部实体类的全文索引。
   * 
   * @param entityClasses 实体类列表
   */
  public void startAndWait(Class<?>... entityClasses) {
    try {
      log.info("开始重建索引...");
      createIndexer(entityClasses).startAndWait();
      log.info("完成重建索引。");
    } catch (Exception e) {
      throw new SysException("重建全文索引时发生异常。", e);
    }
  }

  /**
   * 异步创建指定实体类的全文索引。
   * 
   * @param entityClasses 实体类列表
   */
  public void start(Class<?>... entityClasses) {
    try {
      createIndexer(entityClasses).start();
    } catch (Exception e) {
      throw new SysException("重建全文索引时发生异常。", e);
    }
  }

  /**
   * 创建索引构建组件。
   * 
   * @param entityClasses 业务实体类
   * @return 返回索引构建组件。
   */
  private MassIndexer createIndexer(Class<?>... entityClasses) {
    FullTextEntityManager entityManager =
        Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
    return entityManager.createIndexer(entityClasses).typesToIndexInParallel(2)
        .threadsToLoadObjects(20).batchSizeToLoadObjects(25).cacheMode(CacheMode.NORMAL);
  }
}
