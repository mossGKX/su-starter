package com.cntest.su.xss.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.HtmlUtils;

import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.xss.util.XssUtils;

public class XssFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
    filterChain.doFilter(xssRequest, response);
  }

  class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
      super(request);
    }

    @Override
    public String getParameter(String name) {
      String value = super.getParameter(name);
      return XssUtils.clear(value);
    }

    @Override
    public String[] getParameterValues(String parameter) {
      String[] values = super.getParameterValues(parameter);
      if (CollectionUtils.isNotEmpty(values)) {
        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
          encodedValues[i] = XssUtils.clear(values[i]);
        }
        values = encodedValues;
      }
      return values;
    }

    @Override
    public String getHeader(String name) {
      String value = super.getHeader(HtmlUtils.htmlEscape(name));
      return XssUtils.clear(value);
    }
  }
}
