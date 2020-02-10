package com.cntest.su.auth.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.auth.client.AuthClientRealm;
import com.cntest.su.auth.config.ShiroConfigurationAware;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
public class AuthClientAutoConfiguration implements ShiroConfigurationAware {
  /**
   * 配置认证组件。
   * 
   * @return 返回认证组件。
   */
  @Override
  @Bean
  public AuthClientRealm authRealm() {
    return new AuthClientRealm();
  }
}
