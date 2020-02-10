package com.cntest.su.auth;

/**
 * 认证用户。
 */
public interface AuthUser {
  /**
   * 获取用户名。
   * 
   * @return 返回用户名。
   */
  String getUsername();

  /**
   * 获取密码。
   * 
   * @return 返回密码。
   */
  String getPassword();

  /**
   * 是否可用。
   * 
   * @return 返回是否可用。
   */
  Boolean getEnabled();
}
