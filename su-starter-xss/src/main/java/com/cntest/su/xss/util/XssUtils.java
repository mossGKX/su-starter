package com.cntest.su.xss.util;

import org.springframework.web.util.HtmlUtils;

import com.cntest.su.utils.StringUtils;

/**
 * XSS工具类。
 */
public class XssUtils {
  /**
   * 清除XSS攻击代码。
   * 
   * @param content 提交的内容
   * @return 返回清理后的内容。
   */
  public static String clear(String content) {
    if (StringUtils.isNotBlank(content)) {
      String escapedContent = HtmlUtils.htmlEscape(content);
      // 转义SQL代码
      escapedContent = escapedContent.replaceAll("'", "&#39;").replaceAll("\\(", "&#40;")
          .replaceAll("\\)", "&#41;");
      return escapedContent;
    }
    return content;
  }

  private XssUtils() {}
}
