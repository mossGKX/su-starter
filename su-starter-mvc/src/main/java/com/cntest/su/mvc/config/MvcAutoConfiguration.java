package com.cntest.su.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cntest.su.mvc.handler.GenericWebServerFactoryCustomizer;

@Configuration
public class MvcAutoConfiguration implements WebMvcConfigurer {
  @Bean("com.cntest.su.mvc.config.FreeMarkerSettings")
  public FreeMarkerSettings freemarkerSettings() {
    return new FreeMarkerSettings();
  }

  @Bean
  public GenericWebServerFactoryCustomizer webServerFactoryCustomizer() {
    return new GenericWebServerFactoryCustomizer();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/mvc/**")
        .addResourceLocations("classpath:/META-INF/su/mvc/static/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/index");

    registry.addViewController("/500").setViewName("/500");
    registry.addViewController("/404").setViewName("/404");
    registry.addViewController("/403").setViewName("/403");
  }
}
