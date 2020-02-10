package com.cntest.su.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring工具类。
 */
public class SpringUtils implements ApplicationContextAware {
  private static ApplicationContext context;

  @Override
  public synchronized void setApplicationContext(ApplicationContext context) {
    SpringUtils.context = context;
  }

  /**
   * 获取Spring容器的应用上下文。
   * 
   * @return 返回Spring容器的应用上下文。
   */
  public static ApplicationContext getContext() {
    return context;
  }

  /**
   * 从Spring容器中获取指定名称的Bean。
   * 
   * @param <T> bean类型
   * @param beanName bean名称
   * @return 返回指定名称的bean。
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String beanName) {
    return (T) context.getBean(beanName);
  }

  /**
   * 从Spring容器中获取指定类型的Bean。
   * 
   * @param <T> bean类型
   * @param beanType bean类型
   * @return 返回指定类型的bean。
   */
  public static <T> T getBean(Class<T> beanType) {
    return context.getBean(beanType);
  }
}
