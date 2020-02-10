package com.cntest.su.kaptcha.config;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("su.kaptcha")
public class KaptchaProperties {
  /** 是否带边框 */
  private String border = "yes";
  /** 边框颜色 */
  private String borderColor = "white";
  /** 是否带噪音 */
  private String noise = "no";
  /** 噪音颜色 */
  private String noiseColor = "blue";
  /** 字体名称 */
  private String fontNames = "宋体,楷体,微软雅黑";
  /** 字体大小 */
  private String fontSize = "25";
  /** 字体颜色 */
  private String fontColor = "black";
  /** 图片宽度 */
  private String imageWidth = "70";
  /** 图片高度 */
  private String imageHeight = "32";
  /** 验证码字符集 */
  private String charString = "abcdefghijklmnopqrstuvwxyz0123456789";
  /** 验证码长度 */
  private String charLength = "4";
  /** 验证码字符间隔 */
  private String charSpace = "3";
  /** 背景渐变起始颜色 */
  private String backgroundClearFrom = "white";
  /** 背景渐变截止颜色 */
  private String backgroundClearTo = "white";

  /**
   * 转换成Kaptcha的配置属性。
   * 
   * @return 返回Kaptcha的配置属性。
   */
  public Properties toProperties() {
    Properties properties = new Properties();
    properties.setProperty("kaptcha.border", border);
    properties.setProperty("kaptcha.border.color", borderColor);
    properties.setProperty("kaptcha.textproducer.font.names", fontNames);
    properties.setProperty("kaptcha.textproducer.font.size", fontSize);
    properties.setProperty("kaptcha.textproducer.font.color", fontColor);
    properties.setProperty("kaptcha.image.width", imageWidth);
    properties.setProperty("kaptcha.image.height", imageHeight);
    properties.setProperty("kaptcha.textproducer.char.string", charString);
    properties.setProperty("kaptcha.textproducer.char.length", charLength);
    properties.setProperty("kaptcha.textproducer.char.space", charSpace);
    if ("no".equals(noise)) {
      properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
    }
    properties.setProperty("kaptcha.noise.color", noiseColor);
    properties.setProperty("kaptcha.background.clear.from", backgroundClearFrom);
    properties.setProperty("kaptcha.background.clear.to", backgroundClearTo);
    return properties;
  }
}
