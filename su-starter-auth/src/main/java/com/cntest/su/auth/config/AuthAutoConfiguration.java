package com.cntest.su.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.auth.AuthHelper;
import com.cntest.su.auth.handle.AuthErrorController;

/**
 * 组件配置。
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthAutoConfiguration {
  @Bean
  public AuthHelper authHelper() {
    return new AuthHelper();
  }

  @Bean
  public AuthErrorController authErrorController() {
    return new AuthErrorController();
  }
}
