package com.cntest.su.dfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 文件存储通用配置。
 */
@Data
@ConfigurationProperties("su.dfs")
public class DfsProperties {
  private String type = "local";
  private String site = "http://localhost:8080/";
}
