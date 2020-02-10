package com.cntest.su.auth.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;

public interface ShiroConfigurationAware {
  AuthorizingRealm authRealm();

  /**
   * 配置SecurityManager组件。
   * 
   * @return 返回SecurityManager组件。
   */
  @Bean
  public default SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(authRealm());
    return securityManager;
  }

  /**
   * 配置DefaultAdvisorAutoProxyCreator组件。
   * 
   * @return 返回DefaultAdvisorAutoProxyCreator组件。
   */
  @Bean
  public default DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =
        new DefaultAdvisorAutoProxyCreator();
    defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
    return defaultAdvisorAutoProxyCreator;
  }

  /**
   * 配置AuthorizationAttributeSourceAdvisor组件。
   * 
   * @param securityManager SecurityManager组件
   * @return 返回AuthorizationAttributeSourceAdvisor组件。
   */
  @Bean
  public default AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
  }

  /**
   * 配置LifecycleBeanPostProcessor组件。
   * 
   * @return 返回LifecycleBeanPostProcessor组件。
   */
  @Bean
  public default LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 配置ShiroFilter组件。
   * 
   * @param securityManager SecurityManager组件
   * @return 返回ShiroFilter组件。
   */
  @Bean
  public default ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
    factory.setSecurityManager(securityManager);
    factory.setUnauthorizedUrl("/401");
    return factory;
  }
}
