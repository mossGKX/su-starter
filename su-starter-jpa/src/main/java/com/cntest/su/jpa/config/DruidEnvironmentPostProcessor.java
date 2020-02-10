package com.cntest.su.jpa.config;

import java.util.Properties;

import com.cntest.su.config.GenericEnvironmentPostProcessor;

public class DruidEnvironmentPostProcessor implements GenericEnvironmentPostProcessor {
  @Override
  public String getPropertiesName() {
    return "druidDefaultProperties";
  }

  @Override
  public String getEnablePropertyName() {
    return "spring.datasource.druid.enable-default-config";
  }

  @Override
  public Properties getProperties() {
    Properties props = new Properties();
    props.put("spring.datasource.druid.max-wait", 15000);
    props.put("spring.datasource.druid.pool-prepared-statements", false);
    props.put("spring.datasource.druid.max-open-prepared-statements", 0);
    props.put("spring.datasource.druid.validation-query", "select 'x'");
    props.put("spring.datasource.druid.test-while-idle", true);
    props.put("spring.datasource.druid.test-on-borrow", true);
    props.put("spring.datasource.druid.test-on-return", false);
    props.put("spring.datasource.druid.time-between-eviction-runs-millis", 60000);
    props.put("spring.datasource.druid.min-evictable-idle-time-millis", 300000);
    props.put("spring.datasource.druid.remove-abandoned", false);
    props.put("spring.datasource.druid.remove-abandoned-timeout", 1800);
    props.put("spring.datasource.druid.log-abandoned", true);
    props.put("spring.datasource.druid.filters", "stat,slf4j");
    props.put("spring.datasource.druid.web-stat-filter.session-stat-enable", false);
    return props;
  }
}
