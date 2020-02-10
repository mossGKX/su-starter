package com.cntest.su.auth.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.auth.feign.handle.AuthRequestInterceptor;

@Configuration
public class AuthFeignAutoConfiguration {
  @Bean
  AuthRequestInterceptor authRequestInterceptor() {
    return new AuthRequestInterceptor();
  }
}
