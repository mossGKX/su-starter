package com.cntest.su.dwz.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cntest.su.auth.AuthUser;
import com.cntest.su.auth.AuthUserService;
import com.cntest.su.exception.BizException;
import com.cntest.su.kaptcha.Kaptcha;
import com.cntest.su.message.MessageSource;

/**
 * 登录基类。
 */
public abstract class AbstractLoginAction {
  @Autowired
  protected AuthUserService<? extends AuthUser> authUserService;
  @Autowired
  protected Kaptcha captcha;
  @Autowired
  protected AuthCounter authCounter;
  @Autowired
  protected MessageSource messageSource;

  /**
   * 查看登录页面。
   * 
   * @param model 数据模型
   */
  @RequestMapping("login")
  public void login(Model model) {
    model.addAttribute(new LoginDto());
    model.addAttribute(authCounter);
  }

  /**
   * 验证登录。
   * 
   * @param model 数据模型
   * @param request 请求对象
   * @param dto 登录数据模型
   * @param errors 错误信息
   * 
   * @return 登录成功返回系统首页，失败返回登录页面。
   */
  @RequestMapping("login-auth")
  public String auth(Model model, HttpServletRequest request, LoginDto dto, BindingResult errors) {
    try {
      if (authCounter.isOver()) {
        captcha.verify(dto.getCode());
      }
      authUserService.login(dto.getUsername(), dto.getPassword());
      authCounter.clean();
      return "redirect:/index";
    } catch (UnknownAccountException | IncorrectCredentialsException e) {
      errors.reject("none", messageSource.get("E980"));
      authCounter.add();
    } catch (DisabledAccountException e) {
      errors.reject("none", messageSource.get("E981"));
    } catch (BizException e) {
      errors.reject("none", e.getMessage());
    }
    model.addAttribute(authCounter);
    return "login";
  }

  /**
   * 退出登录。
   * 
   * @return 返回登录页面。
   */
  @RequestMapping("logout")
  public String logout() {
    authUserService.logout();
    return "redirect:/login";
  }
}
