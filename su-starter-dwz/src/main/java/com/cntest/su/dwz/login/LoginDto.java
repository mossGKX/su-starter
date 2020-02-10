package com.cntest.su.dwz.login;

/**
 * 登录Model。
 */
public class LoginDto {
  /** 用户名 */
  private String username;
  /** 密码 */
  private String password;
  /** 验证码 */
  private String code;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
