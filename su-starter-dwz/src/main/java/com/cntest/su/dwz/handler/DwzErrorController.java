package com.cntest.su.dwz.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.cntest.su.dwz.result.DwzResultBuild;
import com.cntest.su.exception.BizException;
import com.cntest.su.message.MessageSource;
import com.cntest.su.utils.StringUtils;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DwzErrorController {
  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(BindException.class)
  public ModelAndView handleBind(BindException e) {
    return genErrorModelAndView(e.getBindingResult());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ModelAndView handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    return genErrorModelAndView(e.getBindingResult());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ModelAndView handleConstraintViolation(ConstraintViolationException e) {
    List<String> errorMsgs = new ArrayList<>();
    Set<ConstraintViolation<?>> bindErrors = e.getConstraintViolations();
    for (ConstraintViolation<?> bindError : bindErrors) {
      errorMsgs.add(bindError.getMessage());
    }
    return new DwzResultBuild().error(messageSource.get("E990", StringUtils.join(errorMsgs, "|")))
        .buildView();
  }

  @ExceptionHandler(BizException.class)
  public ModelAndView handleBiz(BizException e) {
    return new DwzResultBuild().error(e.getMessage()).buildView();
  }

  @ExceptionHandler(AuthorizationException.class)
  public ModelAndView handleAuthentication(HttpServletRequest request, AuthorizationException e) {
    if (isAjaxRequest(request)) {
      return new DwzResultBuild().timeout().buildView();
    } else {
      return new ModelAndView("redirect:/login");
    }
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ModelAndView handleAuthorization(HttpServletRequest request) {
    if (isAjaxRequest(request)) {
      return new DwzResultBuild().denied().buildView();
    } else {
      return new ModelAndView("redirect:/login");
    }
  }

  private ModelAndView genErrorModelAndView(BindingResult bindingResult) {
    List<String> errorMsgs = new ArrayList<>();
    List<ObjectError> bindErrors = bindingResult.getAllErrors();
    for (ObjectError bindError : bindErrors) {
      if (bindError instanceof FieldError) {
        FieldError fieldError = (FieldError) bindError;
        errorMsgs.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
      } else {
        errorMsgs.add(bindError.getDefaultMessage());
      }
    }
    return new DwzResultBuild().error(messageSource.get("E990", StringUtils.join(errorMsgs, "|")))
        .buildView();
  }

  /**
   * 判断是否AJAX请求。
   * 
   * @param request 请求对象
   * @return 返回是否AJAX请求。
   */
  private Boolean isAjaxRequest(HttpServletRequest request) {
    return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
  }
}
