package com.cntest.su.job.manager.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.job.config.JobProperties;
import com.cntest.su.job.manager.JobFactory;
import com.cntest.su.job.manager.JobLogManager;
import com.cntest.su.job.manager.JobManager;

@Configuration
@EnableConfigurationProperties(JobProperties.class)
public class JobManagerAutoConfiguration {
  @Bean
  JobFactory jobFactory() {
    return new JobFactory();
  }

  @Bean
  JobManager jobManager() {
    return new JobManager();
  }

  @Bean
  JobLogManager jobLogManager() {
    return new JobLogManager();
  }
}
