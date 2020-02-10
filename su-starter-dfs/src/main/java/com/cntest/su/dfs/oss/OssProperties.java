package com.cntest.su.dfs.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("oss")
public class OssProperties {
  private String endpoint;
  private String accessKeyId;
  private String accessKeySecret;
  private String defaultBucketName;
}
