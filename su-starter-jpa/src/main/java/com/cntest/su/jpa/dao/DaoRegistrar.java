package com.cntest.su.jpa.dao;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.type.AnnotationMetadata;

import com.cntest.su.registrar.PackageScanRegistrar;

import lombok.extern.slf4j.Slf4j;

/**
 * 该类实现动态生成Dao组件。
 */
@Slf4j
public class DaoRegistrar implements PackageScanRegistrar {
  @Override
  public void registerBeanDefinitions(AnnotationMetadata metadata,
      BeanDefinitionRegistry registry) {
    for (Class<?> entityType : findClassesByAnnotationClass(metadata, EntityScan.class,
        Entity.class)) {
      AnnotatedGenericBeanDefinition daoDefinition = genDaoDefinition(entityType);
      String daoBeanName = genDaoBeanName(entityType);
      registry.registerBeanDefinition(daoBeanName, daoDefinition);
      log.info("自动为实体 [{}] 生成Dao组件 [{}]。", entityType.getSimpleName(), daoBeanName);
    }
  }

  /**
   * 生成DAO组件定义。
   * 
   * @param entityClass 实体类
   * @return 返回DAO组件定义。
   */
  protected AnnotatedGenericBeanDefinition genDaoDefinition(Class<?> entityClass) {
    AnnotatedGenericBeanDefinition daoDefinition = new AnnotatedGenericBeanDefinition(Dao.class);
    ConstructorArgumentValues av = new ConstructorArgumentValues();
    av.addGenericArgumentValue(entityClass);
    daoDefinition.setConstructorArgumentValues(av);
    return daoDefinition;
  }

  /**
   * 生成DAO组件名称。
   * 
   * @param entityClass 实体类
   * @return 返回DAO组件名称。
   */
  private String genDaoBeanName(Class<?> entityClass) {
    String beanName = entityClass.getSimpleName();
    char[] chars = beanName.toCharArray();
    chars[0] = Character.toLowerCase(chars[0]);
    return new String(chars) + "Dao";
  }
}
