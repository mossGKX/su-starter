package com.cntest.su.auth.feign.handle;

import org.springframework.web.context.request.RequestContextHolder;

import com.cntest.su.auth.constant.TokenIds;
import com.cntest.su.utils.StringUtils;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign认证拦截器。
 */
public class AuthRequestInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate template) {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    if (StringUtils.isNotBlank(sessionId)) {
      template.header(TokenIds.X_AUTH_TOKEN, sessionId);
    }
  }
}
