package com.cntest.su.kaptcha.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cntest.su.kaptcha.Kaptcha;
import com.cntest.su.utils.StringUtils;

@Controller
@RequestMapping("/")
public class KaptchaAction {
  @Autowired
  private Kaptcha kaptcha;

  /**
   * 输出验证码图片。
   * 
   * @param request HTTP请求
   * @param out 页面输出流
   * @throws IOException
   * 
   * @throws IOException 图片输出失败时抛出异常。
   */
  @RequestMapping("kaptcha-image")
  public void kaptchaImage(HttpServletRequest request, OutputStream out) throws IOException {
    if (StringUtils.isNotBlank(request.getParameter("timestamp"))) {
      ImageIO.write(kaptcha.genImage(), "JPEG", out);
    } else {
      ImageIO.write(kaptcha.getCurrentImage(), "JPEG", out);
    }
  }
}
