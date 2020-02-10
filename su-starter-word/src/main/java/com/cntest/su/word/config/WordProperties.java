package com.cntest.su.word.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("su.word")
public class WordProperties {
  /** 模版路径 */
  private String templateDir = "classpath*:/META-INF/su/word";
}
