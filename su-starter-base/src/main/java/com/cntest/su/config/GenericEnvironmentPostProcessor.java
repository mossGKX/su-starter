package com.cntest.su.config;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * 默认参数设置处理接口，方便设置第三方组件的默认参数配置。
 */
public interface GenericEnvironmentPostProcessor extends EnvironmentPostProcessor {
  /**
   * 获取默认参数配置的名称。
   * 
   * @return 返回默认参数配置的名称。
   */
  String getPropertiesName();

  /**
   * 获取是否启用默认参数设置的属性名。
   * 
   * @return 返回是否启用默认参数设置的属性名。
   */
  String getEnablePropertyName();

  /**
   * 获取默认参数配置。
   * 
   * @return 返回默认参数配置。
   */
  Properties getProperties();

  @Override
  default void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    Boolean enableDefaultConfig =
        environment.getProperty(getEnablePropertyName(), Boolean.class, Boolean.TRUE);
    if (enableDefaultConfig) {
      PropertiesPropertySource propertiesPropertySource =
          new PropertiesPropertySource(getPropertiesName(), getProperties());
      environment.getPropertySources().addLast(propertiesPropertySource);
    }
  }
}
