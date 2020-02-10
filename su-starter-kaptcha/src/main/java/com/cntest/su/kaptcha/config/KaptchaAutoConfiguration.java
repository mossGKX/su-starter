package com.cntest.su.kaptcha.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.kaptcha.Kaptcha;
import com.cntest.su.kaptcha.action.KaptchaAction;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {
  @Bean
  public KaptchaAction kaptchaAction() {
    return new KaptchaAction();
  }

  @Bean
  public Kaptcha kaptcha() {
    return new Kaptcha();
  }

  @Bean
  public DefaultKaptcha defaultKaptcha(KaptchaProperties properties) {
    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    Config config = new Config(properties.toProperties());
    defaultKaptcha.setConfig(config);
    return defaultKaptcha;
  }
}
