package com.cntest.su.process.activiti.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("spring.activiti.datasource")
public class ActivitiProperties {

  private String url;

  private String username;

  private String password;

  private String driverClassName;

  private Integer initialSize;

  private Integer minIdle;

  private Integer maxActive;

  private Integer maxWait;

  private Integer timeBetweenEvictionRunsMillis;

  private Integer minEvictableIdleTimeMillis;

  private String validationQuery;

  private Boolean testWhileIdle;

  private Boolean testOnBorrow;

  private Boolean testOnReturn;

  private String filters;

  private String logSlowSql;

  private Boolean removeAbandoned;

  private Integer removeAbandonedTimeout;

  private Boolean logAbandoned;

  private Boolean defaultAutoCommit;

}
