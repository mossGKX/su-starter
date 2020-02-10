package com.cntest.su.jpa.config;

import java.util.Properties;

import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;

import com.cntest.su.config.GenericEnvironmentPostProcessor;
import com.integralblue.hibernate.cache.springcache.SpringCacheRegionFactory;

public class JpaEnvironmentPostProcessor implements GenericEnvironmentPostProcessor {
  @Override
  public String getPropertiesName() {
    return "jpaDefaultProperties";
  }

  @Override
  public String getEnablePropertyName() {
    return "spring.jpa.hibernate.enable-default-config";
  }

  @Override
  public Properties getProperties() {
    Properties props = new Properties();
    props.put("spring.jpa.hibernate.ddl-auto", "none");
    props.put("spring.jpa.hibernate.naming.physical-strategy",
        PhysicalNamingStrategyStandardImpl.class.getName());
    props.put("spring.jpa.properties.hibernate.cache.use_query_cache", true);
    props.put("spring.jpa.properties.hibernate.cache.region.factory_class",
        SpringCacheRegionFactory.class.getName());
    return props;
  }
}
