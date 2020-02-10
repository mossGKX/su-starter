package com.cntest.su.dfs.obs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("obs")
public class ObsProperties {
  private String endpoint;
  private String accessKeyId;
  private String accessKeySecret;
  private String defaultBucketName;
}
