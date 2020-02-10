package com.cntest.su.dfs.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.cntest.su.dfs.fastdfs.FastDfsAutoConfiguration;
import com.cntest.su.dfs.local.LocalDfsAutoConfiguration;
import com.cntest.su.dfs.obs.ObsAutoConfiguration;
import com.cntest.su.dfs.oss.OssAutoConfiguration;

@Configuration
@EnableConfigurationProperties(DfsProperties.class)
@ImportAutoConfiguration({LocalDfsAutoConfiguration.class, FastDfsAutoConfiguration.class,
    ObsAutoConfiguration.class, OssAutoConfiguration.class})
public class DfsAutoConfiguration {
}
