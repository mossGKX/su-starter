package com.cntest.su.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.message.MessageSource;
import com.cntest.su.utils.SpringUtils;

/**
 * 组件配置。
 */
@Configuration
public class BaseAutoConfiguration {
  /**
   * 配置信息组件。
   * 
   * @return 返回信息组件。
   */
  @Bean
  public MessageSource messageSource() {
    return new MessageSource();
  }

  /**
   * 配置Spring工具组件。
   * 
   * @return 返回Spring工具组件。
   */
  @Bean
  public SpringUtils springUtils() {
    return new SpringUtils();
  }
}
