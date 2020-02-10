package com.cntest.su.auth.handle;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cntest.su.message.MessageSource;
import com.cntest.su.web.handler.ErrorResponse;

/**
 * 异常处理拦截器。
 */
@RestControllerAdvice
public class AuthErrorController {
  @Autowired
  private MessageSource messageSource;

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthorizationException.class)
  public ErrorResponse handleAuthentication() {
    return getErrorResponse("E401");
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(UnauthorizedException.class)
  public ErrorResponse handleAuthorization() {
    return getErrorResponse("E403");
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler({UnknownAccountException.class, IncorrectCredentialsException.class})
  public ErrorResponse handleUnknownAccountOrIncorrectCredentials() {
    return getErrorResponse("E980");
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(DisabledAccountException.class)
  public ErrorResponse handleDisabledAccount() {
    return getErrorResponse("E981");
  }

  private ErrorResponse getErrorResponse(String errorCode) {
    return new ErrorResponse(errorCode, messageSource.get(errorCode));
  }
}
