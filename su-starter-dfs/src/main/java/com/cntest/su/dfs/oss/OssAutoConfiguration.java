package com.cntest.su.dfs.oss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.dfs.DfsClient;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(name = "su.dfs.type", havingValue = "oss", matchIfMissing = false)
public class OssAutoConfiguration {
  @Bean
  DfsClient ossClient() {
    return new OssDfsClient();
  }
}
