package com.cntest.su.freemarker.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.cntest.su.freemarker.NoWebFreeMarkerConfigurer;

/**
 * 非web环境下的FreeMarker配置。
 */
@Configuration
@ConditionalOnNotWebApplication
public class NoWebFreeMarkerAutoConfiguration {
  /**
   * 配置FreeMarkerConfigurationFactoryBean组件。
   * 
   * @param freemarkerProperties FreeMarker配置属性
   * @return 返回FreeMarkerConfigurationFactoryBean组件。
   */
  @Bean
  public FreeMarkerConfigurationFactoryBean freemarkerConfigurer(
      FreeMarkerProperties freemarkerProperties) {
    NoWebFreeMarkerConfigurer configurer = new NoWebFreeMarkerConfigurer();
    configurer.setFreemarkerSettings(freemarkerProperties.toProperties());
    return configurer;
  }
}
