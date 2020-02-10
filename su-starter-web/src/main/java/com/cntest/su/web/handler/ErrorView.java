package com.cntest.su.web.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import com.cntest.su.constant.Encoding;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 异常视图，替代默认的页面异常视图。
 */
public class ErrorView implements View {
  private WebErrorAttributes errorAttributes;
  private ObjectMapper objectMaaper;

  public ErrorView(ObjectMapper objectMaaper, WebErrorAttributes errorAttributes) {
    this.objectMaaper = objectMaaper;
    this.errorAttributes = errorAttributes;
  }

  @Override
  public String getContentType() {
    return "text/html";
  }

  @Override
  public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    WebRequest webRequest = new ServletWebRequest(request);
    Map<String, Object> errorMap = errorAttributes.getErrorAttributes(webRequest, false);
    ErrorResponse error =
        new ErrorResponse(errorMap.get("code").toString(), errorMap.get("msg").toString());
    response.setHeader("Content-type", "text/html;charset=UTF-8");
    response.setCharacterEncoding(Encoding.UTF8);
    response.getWriter().println(objectMaaper.writeValueAsString(error));
  }
}
