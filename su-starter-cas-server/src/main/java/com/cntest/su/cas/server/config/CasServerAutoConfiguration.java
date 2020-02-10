package com.cntest.su.cas.server.config;

import org.jasig.cas.web.ServiceValidateController;
import org.restlet.ext.spring.RestletFrameworkServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:META-INF/spring/applicationContext.xml",
    "classpath:META-INF/spring/deployerConfigContext.xml",
    "classpath:META-INF/spring/ticketRegistry.xml",
    "classpath:META-INF/spring/uniqueIdGenerators.xml",
    "classpath:META-INF/spring/ticketExpirationPolicies.xml"})
public class CasServerAutoConfiguration {


  @Bean
  public ServletRegistrationBean indexServletRegistration() {
    RestletFrameworkServlet restletFrameworkServlet = new RestletFrameworkServlet();
    ServletRegistrationBean registration =
        new ServletRegistrationBean(restletFrameworkServlet, "/v1/*");
    registration.addInitParameter("contextConfigLocation",
        "classpath:META-INF/spring/restlet-servlet.xml");
    return registration;
  }

  @Bean
  public ServletRegistrationBean servletTLReportServlet() {
    return new ServletRegistrationBean(new TLReportServlet(), "/p3/serviceValidat");
  }

  ServiceValidateController controller = new ServiceValidateController();
}
