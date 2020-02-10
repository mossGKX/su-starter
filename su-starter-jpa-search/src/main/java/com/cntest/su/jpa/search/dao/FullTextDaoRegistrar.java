package com.cntest.su.jpa.search.dao;

import org.hibernate.search.annotations.Indexed;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;

import com.cntest.su.jpa.dao.DaoRegistrar;

/**
 * 该类实现动态生成Dao或FullTextDao组件。
 */
public class FullTextDaoRegistrar extends DaoRegistrar {
  @Override
  protected AnnotatedGenericBeanDefinition genDaoDefinition(Class<?> entityClass) {
    if (entityClass.isAnnotationPresent(Indexed.class)) {
      AnnotatedGenericBeanDefinition daoDefinition =
          new AnnotatedGenericBeanDefinition(FullTextDao.class);
      ConstructorArgumentValues av = new ConstructorArgumentValues();
      av.addGenericArgumentValue(entityClass);
      daoDefinition.setConstructorArgumentValues(av);
      return daoDefinition;
    } else {
      return super.genDaoDefinition(entityClass);
    }
  }
}
