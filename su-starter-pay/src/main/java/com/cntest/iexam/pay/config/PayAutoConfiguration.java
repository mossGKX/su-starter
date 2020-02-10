package com.cntest.iexam.pay.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.iexam.pay.yee.YeePay;
import com.cntest.iexam.pay.yee.YeePayProperties;

@Configuration
@EnableConfigurationProperties(YeePayProperties.class)
public class PayAutoConfiguration {
  @Bean
  public YeePay yeePay() {
    return new YeePay();
  }
}
