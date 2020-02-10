package com.cntest.su.dfs.obs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.dfs.DfsClient;

@Configuration
@EnableConfigurationProperties(ObsProperties.class)
@ConditionalOnProperty(name = "su.dfs.type", havingValue = "obs", matchIfMissing = false)
public class ObsAutoConfiguration {
  @Bean
  DfsClient obsClient() {
    return new ObsDfsClient();
  }
}
