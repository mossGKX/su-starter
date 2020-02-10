package com.cntest.su.dwz.config;

import com.cntest.su.freemarker.AbstractFreeMarkerSettings;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/su/dwz/template/");
    addTemplatePath("classpath:/META-INF/su/dwz/macro/");
    addAutoImport("dwz", "dwz.ftl");

    addTld("/META-INF/su/tld/shiro.tld");
    addAutoInclude("shiro-include.ftl");
    addAutoImport("sec", "sec.ftl");
  }
}
