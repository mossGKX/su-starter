package com.cntest.su.jpa.audit.handle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import com.cntest.su.auth.AuthUser;
import com.cntest.su.auth.AuthUserService;

/**
 * 审计拦截器中获取审计人组件。
 */
public class GenericAuditorAware implements AuditorAware<AuthUser> {
  @Autowired
  private AuthUserService<? extends AuthUser> authUserService;

  @Override
  public Optional<AuthUser> getCurrentAuditor() {
    return Optional.of(authUserService.getDefaultUser());
  }
}
