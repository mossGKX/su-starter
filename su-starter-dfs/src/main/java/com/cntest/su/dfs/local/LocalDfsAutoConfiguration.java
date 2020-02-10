package com.cntest.su.dfs.local;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.dfs.DfsClient;

@Configuration
@EnableConfigurationProperties(LocalDfsProperties.class)
@ConditionalOnProperty(name = "su.dfs.type", havingValue = "ldfs", matchIfMissing = true)
public class LocalDfsAutoConfiguration {
  @Bean
  DfsClient localDfsClient() {
    return new LocalDfsClient();
  }
}
