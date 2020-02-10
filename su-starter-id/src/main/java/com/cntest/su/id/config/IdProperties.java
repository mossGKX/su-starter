package com.cntest.su.id.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("su.id")
public class IdProperties {
  private String type = "ip";
}
