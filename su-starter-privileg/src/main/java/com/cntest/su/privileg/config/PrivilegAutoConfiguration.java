package com.cntest.su.privileg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.privileg.PrivilegManager;

/**
 * 组件配置。
 */
@Configuration
public class PrivilegAutoConfiguration {
  /**
   * 配置权限管理组件。
   * 
   * @return 返回权限管理组件。
   */
  @Bean
  public PrivilegManager privilegManager() {
    return new PrivilegManager();
  }
}
