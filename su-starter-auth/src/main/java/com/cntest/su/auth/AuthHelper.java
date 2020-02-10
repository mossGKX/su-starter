package com.cntest.su.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.auth.config.AuthProperties;

/**
 * 认证辅助工具组件。
 */
public class AuthHelper {
  @Autowired
  private AuthProperties properties;

  /**
   * 创建密码验证器。
   * 
   * @return 返回密码验证器。
   */
  public HashedCredentialsMatcher createMatcher() {
    HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(properties.getAlgorithmName());
    matcher.setStoredCredentialsHexEncoded(properties.getHexEncode());
    return matcher;
  }

  /**
   * 加密密码。
   * 
   * @param password 待加密的密码
   * @return 返回加密后的密码。
   */
  public String encodePassword(String password) {
    SimpleHash hash = new SimpleHash(properties.getAlgorithmName(), password, getSaltByteSource());
    if (properties.getHexEncode()) {
      return hash.toHex();
    } else {
      return hash.toBase64();
    }
  }

  /**
   * 检查密码是否正确。
   * 
   * @param password 原密码
   * @param hashedPassword 加密后的密码
   * @return 如果密码正确返回true，否则返回false。
   */
  public Boolean verifyPassword(String password, String hashedPassword) {
    return encodePassword(password).equals(hashedPassword);
  }

  /**
   * 获取盐值字节源。
   * 
   * @return 返回盐值字节源。
   */
  public ByteSource getSaltByteSource() {
    if (properties.getSalted()) {
      return ByteSource.Util.bytes(properties.getSalt());
    } else {
      return null;
    }
  }

  /**
   * 将新的认证令牌应用到当前会话。
   * 
   * @param authToken 认证令牌
   */
  public void applyToken(AuthToken authToken) {
    Subject subject = SecurityUtils.getSubject();
    PrincipalCollection origPrincipals = subject.getPrincipals();
    String realmName = origPrincipals.getRealmNames().iterator().next();
    PrincipalCollection principals = new SimplePrincipalCollection(authToken, realmName);
    subject.runAs(principals);
  }

  /**
   * 获取当前会话认证令牌。
   * 
   * @return 返回当前会话认证令牌。
   */
  @SuppressWarnings("unchecked")
  public <T extends AuthToken> T getAuthToken() {
    try {
      return (T) SecurityUtils.getSubject().getPrincipal();
    } catch (Exception e) {
      throw new UnauthenticatedException("获取当前会话认证令牌时发生异常。", e);
    }
  }
}
