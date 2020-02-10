package com.cntest.su.excel.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.excel.ExcelFactory;

@Configuration
@EnableConfigurationProperties(ExcelProperties.class)
public class ExcelAutoConfiguration {
  @Bean
  public ExcelFactory excelFactory() {
    return new ExcelFactory();
  }
}
