package com.cntest.su.jpa.search.config;

import java.util.Properties;

import com.cntest.su.config.GenericEnvironmentPostProcessor;

public class JpaSearchEnvironmentPostProcessor implements GenericEnvironmentPostProcessor {
  @Override
  public String getPropertiesName() {
    return "jpaSearchDefaultProperties";
  }

  @Override
  public String getEnablePropertyName() {
    return "spring.jpa.hibernate.search.enable-default-config";
  }

  @Override
  public Properties getProperties() {
    Properties props = new Properties();
    props.put("spring.jpa.properties.hibernate.search.default.indexmanager", "elasticsearch");
    props.put("spring.jpa.properties.hibernate.search.default.elasticsearch.required_index_status",
        "yellow");
    return props;
  }
}
