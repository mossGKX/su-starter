package com.cntest.su.freemarker.config;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.cntest.su.constant.Encoding;
import com.cntest.su.utils.DateUtils;

import lombok.Data;

/**
 * FreeMarker配置属性。
 */
@Data
@ConfigurationProperties(prefix = "su.freemarker")
public class FreeMarkerProperties {
  private Integer templateUpdateDelay = 5;
  private String urlEscapingCharset = Encoding.UTF8;
  private String defaultEncoding = Encoding.UTF8;
  private String outputEncoding = Encoding.UTF8;
  private String locale = "";
  private String datetimeFormat = DateUtils.SECOND;
  private String dateFormat = DateUtils.DAY;
  private String timeFormat = "HH:mm:ss";
  private String numberFormat = "#";
  private String booleanFormat = "true,false";
  private String classicCompatible = "true";
  private String whitespaceStripping = "true";

  /**
   * 转换为Properties。
   * 
   * @return 返回Properties。
   */
  public Properties toProperties() {
    Properties properties = new Properties();
    properties.put("template_update_delay", templateUpdateDelay.toString());
    properties.put("url_escaping_charset", urlEscapingCharset);
    properties.put("default_encoding", defaultEncoding);
    properties.put("output_encoding", outputEncoding);
    properties.put("locale", locale);
    properties.put("datetime_format", datetimeFormat);
    properties.put("date_format", dateFormat);
    properties.put("time_format", timeFormat);
    properties.put("number_format", numberFormat);
    properties.put("boolean_format", booleanFormat);
    properties.put("classic_compatible", classicCompatible);
    properties.put("whitespace_stripping", whitespaceStripping);
    return properties;
  }
}
