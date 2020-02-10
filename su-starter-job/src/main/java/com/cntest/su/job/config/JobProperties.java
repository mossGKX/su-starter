package com.cntest.su.job.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务配置。
 */
@Data
@Slf4j
@ConfigurationProperties("su.job")
public class JobProperties {
  private Manager manager;
  private Worker worker;

  @Data
  public static class Manager {
    private String url;
    private Integer workerGroupId;
    private String accessToken;
  }

  @Data
  public static class Worker {
    private String name;
    private String ip;
    private Integer port = 9999;
    private String logPath = "./logs/job";
    private Integer logRetentionDays = -1;

    public Worker() {
      try {
        ip = InetAddress.getLocalHost().getHostAddress();
      } catch (UnknownHostException e) {
        log.warn("通过主机名未取到IP地址。");
      }
    }
  }
}
