package com.cntest.su.kaptcha;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cntest.su.message.MessageSource;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证码组件。
 */
@Slf4j
public class Kaptcha {
  @Autowired
  private DefaultKaptcha defaultKaptcha;
  @Autowired
  private MessageSource messageSource;

  /**
   * 生成验证码图片。
   * 
   * @return 返回验证码图片。
   */
  public BufferedImage genImage() {
    String verifyCode = defaultKaptcha.createText();
    log.debug("生成验证码：{}", verifyCode);
    getSession().setAttribute(defaultKaptcha.getConfig().getSessionKey(), verifyCode);
    return defaultKaptcha.createImage(verifyCode);
  }

  /**
   * 获取当前验证码图片。
   * 
   * @return 返回当前验证码图片。
   */
  public BufferedImage getCurrentImage() {
    Object verifyCode = getSession().getAttribute(defaultKaptcha.getConfig().getSessionKey());
    if (verifyCode == null) {
      return genImage();
    } else {
      return defaultKaptcha.createImage(verifyCode.toString());
    }
  }

  /**
   * 检查验证码。
   * 
   * @param verifyCode 验证码
   */
  public void verify(String verifyCode) {
    Object correctCode = getSession().getAttribute(defaultKaptcha.getConfig().getSessionKey());
    if (correctCode == null) {
      messageSource.thrown("E970");
    } else {
      if (!correctCode.toString().equalsIgnoreCase(verifyCode)) {
        messageSource.thrown("E971");
      }
    }
  }

  /**
   * 获取HttpSession。
   * 
   * @return 返回HttpSession。
   */
  private HttpSession getSession() {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attributes.getRequest().getSession();
  }
}
