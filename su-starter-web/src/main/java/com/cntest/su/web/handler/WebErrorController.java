package com.cntest.su.web.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cntest.su.exception.BizException;
import com.cntest.su.message.MessageSource;
import com.cntest.su.utils.StringUtils;

/**
 * 统一异常处理。
 */
@RestControllerAdvice
public class WebErrorController {
  @Autowired
  private MessageSource messageSource;

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BindException.class)
  public ErrorResponse handleBind(BindException e) {
    return genErrorResponse(e.getBindingResult());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    return genErrorResponse(e.getBindingResult());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(ConstraintViolationException.class)
  public ErrorResponse handleConstraintViolation(ConstraintViolationException e) {
    List<String> errorMsgs = new ArrayList<>();
    Set<ConstraintViolation<?>> bindErrors = e.getConstraintViolations();
    for (ConstraintViolation<?> bindError : bindErrors) {
      String propertyName =
          StringUtils.substringAfterLast(bindError.getPropertyPath().toString(), ".");
      errorMsgs.add(propertyName + ":" + bindError.getMessage());
    }
    return new ErrorResponse("E990", messageSource.get("E990", StringUtils.join(errorMsgs, "|")));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BizException.class)
  public ErrorResponse handleBiz(BizException e) {
    return new ErrorResponse(e.getCode(), e.getMsg());
  }

  private ErrorResponse genErrorResponse(BindingResult bindingResult) {
    List<String> errorMsgs = new ArrayList<>();
    List<ObjectError> bindErrors = bindingResult.getAllErrors();
    for (ObjectError bindError : bindErrors) {
      if (bindError instanceof FieldError) {
        FieldError fieldError = (FieldError) bindError;
        errorMsgs.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
      } else {
        errorMsgs.add(bindError.getDefaultMessage());
      }
    }
    return new ErrorResponse("E990", messageSource.get("E990", StringUtils.join(errorMsgs, "|")));
  }
}
