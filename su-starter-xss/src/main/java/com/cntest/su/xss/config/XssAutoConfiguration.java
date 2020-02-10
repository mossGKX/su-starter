package com.cntest.su.xss.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.xss.filter.XssFilter;
import com.cntest.su.xss.jackson.XssModule;

@Configuration
public class XssAutoConfiguration {
  @Bean
  FilterRegistrationBean<XssFilter> testFilterRegistration() {
    FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new XssFilter());
    registration.addUrlPatterns("/*");
    registration.setName("xssFilter");
    registration.setOrder(1);
    return registration;
  }

  @Bean
  XssModule xssModule() {
    return new XssModule();
  }
}
