package com.cntest.su.dfs.local;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("ldfs")
public class LocalDfsProperties {
  private String localDir = FileUtils.getUserDirectoryPath();
}
