package com.cntest.su.ienum.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cntest.su.ienum.jackson.IEnumModule;
import com.fasterxml.jackson.databind.Module;

@Configuration
@Import({IEnumJpaAutoConfiguration.class, IEnumWebAutoConfiguration.class})
public class IEnumAutoConfiguration {
  /**
   * 配置IEnum Jackson转换组件。
   * 
   * @return 返回IEnum Jackson转换组件。
   */
  @Bean
  @ConditionalOnClass(Module.class)
  public IEnumModule ienumModule() {
    return new IEnumModule();
  }
}
