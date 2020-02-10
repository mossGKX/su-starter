package com.cntest.su.mvc.config;

import com.cntest.su.freemarker.AbstractFreeMarkerSettings;
import com.cntest.su.utils.DateUtils;
import com.cntest.su.utils.StringUtils;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/su/mvc/macro/");
    addTemplatePath("classpath:/META-INF/su/mvc/template/");
    addAutoImport("s", "mvc.ftl");
    addStaticClass(StringUtils.class, DateUtils.class);
  }
}
