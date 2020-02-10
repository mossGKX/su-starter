package com.cntest.su.auth.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.auth.AuthHelper;
import com.cntest.su.auth.AuthToken;
import com.cntest.su.auth.AuthUser;
import com.cntest.su.auth.AuthUserService;
import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 认证组件。
 */
public class AuthServerRealm extends AuthorizingRealm {
  @Autowired
  private AuthUserService<? extends AuthUser> authUserService;
  @Autowired
  private AuthHelper authHelper;

  @Override
  protected void onInit() {
    setCredentialsMatcher(authHelper.createMatcher());
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
    String username = token.getPrincipal().toString();

    AuthUser user = authUserService.getByUsername(username);
    if (user == null) {
      throw new UnknownAccountException();
    }
    if (!user.getEnabled()) {
      throw new DisabledAccountException();
    }

    AuthToken authToken = authUserService.genAuthToken(username);

    // 获取数据库中的用户密码
    String dbPassword = user.getPassword();
    String inPassword = new String((char[]) token.getCredentials());
    // 如果数据库用户密码与传入的密码相同，那么对数据库中密码再次做加密，然后再传入进行比对，解决三方自动登陆问题
    if (dbPassword.equals(inPassword)) {
      dbPassword = this.authHelper.encodePassword(dbPassword);
    }
    return new SimpleAuthenticationInfo(authToken, dbPassword, authHelper.getSaltByteSource(),
        getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    AuthToken authToken = (AuthToken) principals.getPrimaryPrincipal();
    List<String> privilegs = authToken.getPrivilegs();
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addRoles(getRoles(privilegs));
    info.addStringPermissions(privilegs);
    return info;
  }

  /**
   * 获取角色列表。
   * 
   * @return 返回角色列表
   */
  private List<String> getRoles(List<String> privilegs) {
    List<String> roles = new ArrayList<>();
    for (String privileg : privilegs) {
      roles.add(StringUtils.substringBefore(privileg, ":"));
    }
    CollectionUtils.distinct(roles);
    return roles;
  }
}
