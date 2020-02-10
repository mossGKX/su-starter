package com.cntest.su.mail.config;

import com.cntest.su.freemarker.AbstractFreeMarkerSettings;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/su/mail/");
  }
}
