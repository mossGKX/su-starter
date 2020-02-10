package com.cntest.su.id.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.id.IdGeneratorFactoryBean;

@Configuration
@EnableConfigurationProperties(IdProperties.class)
public class IdAutoConfiguration {
  @Bean
  IdGeneratorFactoryBean idGenerator() {
    return new IdGeneratorFactoryBean();
  }
}
