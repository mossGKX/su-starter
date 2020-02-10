package com.cntest.su.swagger.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import springfox.documentation.swagger.web.SwaggerResource;

/**
 * API文档配置属性。
 */
@Data
@ConfigurationProperties(prefix = "su.api.doc")
public class SwaggerProperties {
  private String title;
  private String description;
  private String version;
  private String basePackage;
  private String tokenName = "x-auth-token";
  private List<SwaggerResource> resources = new ArrayList<>();
}
