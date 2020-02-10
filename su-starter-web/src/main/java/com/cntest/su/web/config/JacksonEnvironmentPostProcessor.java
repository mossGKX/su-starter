package com.cntest.su.web.config;

import java.util.Properties;

import com.cntest.su.config.GenericEnvironmentPostProcessor;

public class JacksonEnvironmentPostProcessor implements GenericEnvironmentPostProcessor {
  @Override
  public String getPropertiesName() {
    return "jacksonDefaultProperties";
  }

  @Override
  public String getEnablePropertyName() {
    return "spring.jackson.enable-default-config";
  }

  @Override
  public Properties getProperties() {
    Properties props = new Properties();
    props.put("spring.jackson.default-property-inclusion", "non_null");
    return props;
  }
}
