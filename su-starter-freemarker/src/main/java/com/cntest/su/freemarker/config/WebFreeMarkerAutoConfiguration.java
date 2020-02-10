package com.cntest.su.freemarker.config;

import javax.servlet.Servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.cntest.su.freemarker.WebFreeMarkerConfigurer;

/**
 * web环境下的FreeMarker配置。
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, FreeMarkerConfigurer.class})
public class WebFreeMarkerAutoConfiguration {
  /**
   * 配置FreeMarkerConfigurer组件。
   * 
   * @param freemarkerProperties FreeMarker配置属性
   * @return 返回FreeMarkerConfigurer组件。
   */
  @Bean
  public FreeMarkerConfigurer freemarkerConfigurer(FreeMarkerProperties freemarkerProperties) {
    WebFreeMarkerConfigurer configurer = new WebFreeMarkerConfigurer();
    configurer.setFreemarkerSettings(freemarkerProperties.toProperties());
    return configurer;
  }
}
