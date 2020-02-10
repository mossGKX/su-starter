package com.cntest.su.auth.client;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cntest.su.auth.AuthToken;

/**
 * 认证组件。
 */
public class AuthClientRealm extends AuthorizingRealm {
  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof AuthToken;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
    return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    AuthToken authToken = (AuthToken) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addStringPermissions(authToken.getPrivilegs());
    return info;
  }
}
