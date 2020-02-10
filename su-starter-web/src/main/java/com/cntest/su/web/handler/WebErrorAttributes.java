package com.cntest.su.web.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.cntest.su.message.MessageSource;

public class WebErrorAttributes extends DefaultErrorAttributes {
  @Autowired
  private MessageSource messageSource;

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    Map<String, Object> errorAttributes = new LinkedHashMap<>();
    HttpStatus status = getStatus(webRequest);
    String errorCode = "E" + status.value();
    String msg = messageSource.get(errorCode);
    if (errorCode.equals(msg)) {
      errorCode = "E500";
      msg = messageSource.get(errorCode);
    }
    errorAttributes.put("code", errorCode);
    errorAttributes.put("msg", msg);
    return errorAttributes;
  }

  private HttpStatus getStatus(RequestAttributes requestAttributes) {
    Integer statusCode = (Integer) requestAttributes.getAttribute("javax.servlet.error.status_code",
        RequestAttributes.SCOPE_REQUEST);
    return HttpStatus.valueOf(statusCode);
  }
}
