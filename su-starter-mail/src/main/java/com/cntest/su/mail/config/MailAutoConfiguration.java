package com.cntest.su.mail.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.mail.MailSender;

/**
 * 邮件配置。
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
@ConditionalOnProperty(prefix = "spring.mail", name = "host")
public class MailAutoConfiguration {
  /**
   * 配置邮件发送组件。
   * 
   * @return 返回邮件发送组件。
   */
  @Bean
  MailSender mailSender(MailProperties properties) {
    MailSender sender = new MailSender();
    sender.setHost(properties.getHost());
    if (properties.getPort() != null) {
      sender.setPort(properties.getPort());
    }
    sender.setUsername(properties.getUsername());
    sender.setPassword(properties.getPassword());
    sender.setProtocol(properties.getProtocol());
    if (properties.getDefaultEncoding() != null) {
      sender.setDefaultEncoding(properties.getDefaultEncoding().name());
    }
    if (!properties.getProperties().isEmpty()) {
      sender.setJavaMailProperties(asProperties(properties.getProperties()));
    }
    return sender;
  }

  @Bean("com.cntest.su.mail.config.FreeMarkerSettings")
  FreeMarkerSettings freemarkerSettings() {
    return new FreeMarkerSettings();
  }

  private Properties asProperties(Map<String, String> source) {
    Properties properties = new Properties();
    properties.putAll(source);
    return properties;
  }
}
