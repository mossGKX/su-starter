package com.cntest.su.job.worker.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.job.config.JobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

@Configuration
@EnableConfigurationProperties(JobProperties.class)
public class JobWorkerAutoConfiguration {
  @Bean(initMethod = "start", destroyMethod = "destroy")
  public XxlJobSpringExecutor xxlJobExecutor(JobProperties props) {
    XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
    xxlJobSpringExecutor.setAdminAddresses(props.getManager().getUrl());
    xxlJobSpringExecutor.setAppName(props.getWorker().getName());
    xxlJobSpringExecutor.setIp(props.getWorker().getIp());
    xxlJobSpringExecutor.setPort(props.getWorker().getPort());
    xxlJobSpringExecutor.setAccessToken(props.getManager().getAccessToken());
    xxlJobSpringExecutor.setLogPath(props.getWorker().getLogPath());
    xxlJobSpringExecutor.setLogRetentionDays(props.getWorker().getLogRetentionDays());
    return xxlJobSpringExecutor;
  }
}
