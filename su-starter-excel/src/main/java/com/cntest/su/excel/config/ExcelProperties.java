package com.cntest.su.excel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("su.excel")
public class ExcelProperties {
  /** 模版路径 */
  private String templateDir = "classpath*:/META-INF/su/excel";
}
