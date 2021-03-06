package com.cntest.su.jpa.dao;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Dao组件工具类。
 */
public class DaoUtils implements ApplicationContextAware {
  private static ApplicationContext context;

  /**
   * 获取实体对象。
   * 
   * @param <T> 实体类型
   * @param entityClass 实体类
   * @param id 实体ID
   * @return 返回对应的UuidEntity对象。
   */
  public static <T> T getEntity(Class<T> entityClass, Serializable id) {
    return getDao(entityClass).get(id);
  }

  /**
   * 获取指定实体类的Dao组件。
   * 
   * @param <T> 实体类型
   * @param entityClass 实体类
   * @return 返回对应实体类的Dao组件。
   */
  @SuppressWarnings("unchecked")
  public static <T> Dao<T> getDao(Class<T> entityClass) {
    String daoName = entityClass.getSimpleName();
    char[] chars = daoName.toCharArray();
    chars[0] = Character.toLowerCase(chars[0]);
    daoName = new String(chars) + "Dao";
    return (Dao<T>) context.getBean(daoName);
  }

  @Override
  public synchronized void setApplicationContext(ApplicationContext context) {
    DaoUtils.context = context;
  }
}
