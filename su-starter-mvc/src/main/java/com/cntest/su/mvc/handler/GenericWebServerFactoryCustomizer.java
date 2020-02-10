package com.cntest.su.mvc.handler;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;

/**
 * 自定义嵌入式Servlet容器配置。
 */
public class GenericWebServerFactoryCustomizer
    implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
  @Override
  public void customize(ConfigurableServletWebServerFactory factory) {
    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
    factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
    factory.addErrorPages(new ErrorPage("/500"));
  }
}
