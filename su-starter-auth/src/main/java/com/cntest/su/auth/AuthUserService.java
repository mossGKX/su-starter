package com.cntest.su.auth;

import java.util.ArrayList;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;

/**
 * 认证服务。
 * 
 * @param <U> 认证用户类型
 */
public interface AuthUserService<U extends AuthUser> {
  /**
   * 根据用户名获取认证用户。
   * 
   * @param username 用户名
   * @return 返回指定认证用户。
   */
  U getByUsername(String username);

  /**
   * 生成内部认证Token。默认权限列表为空列表，对于需要权限控制的应用要覆盖重写该方法。
   * 
   * @param username 用户名
   * @return 返回生成的内部认证Token。
   */
  default AuthToken genAuthToken(String username) {
    return new AuthToken(username, new ArrayList<>());
  }

  /**
   * 获取当前会话认证令牌。
   * 
   * @return 返回当前会话认证令牌。
   */
  @SuppressWarnings("unchecked")
  default <T extends AuthToken> T getAuthToken() {
    try {
      return (T) SecurityUtils.getSubject().getPrincipal();
    } catch (Exception e) {
      throw new UnauthenticatedException("获取当前会话认证令牌时发生异常。", e);
    }
  }

  /**
   * 登录。
   * 
   * @param username 用户名
   * @param password 密码
   */
  default void login(String username, String password) {
    AuthenticationToken token = new UsernamePasswordToken(username, password);
    Subject subject = SecurityUtils.getSubject();
    subject.login(token);
    SecurityUtils.getSubject().getSession().setTimeout(1000 * 3600 * 4);
  }

  /**
   * 退出登录。
   */
  default void logout() {
    SecurityUtils.getSubject().logout();
  }

  /**
   * 获取当前登录用户。
   * 
   * @return 返回当前登录用户。
   */
  default U getLogonUser() {
    AuthToken authToken = getAuthToken();
    return getByUsername(authToken.getUsername());
  }

  /**
   * 获取管理员用户。
   * 
   * @return 返回管理员用户。
   */
  default U getAdminUser() {
    return getByUsername("admin");
  }

  /**
   * 获取默认操作用户。
   * 
   * @return 存在登录用户时返回登录用户，否则返回管理员用户。
   */
  default U getDefaultUser() {
    try {
      U user = getLogonUser();
      if (user == null) {
        user = getAdminUser();
      }
      return user;
    } catch (Exception e) {
      return getAdminUser();
    }
  }
}
