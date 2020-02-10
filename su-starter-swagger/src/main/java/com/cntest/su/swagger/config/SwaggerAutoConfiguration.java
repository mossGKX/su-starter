package com.cntest.su.swagger.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerAutoConfiguration {
  /**
   * 配置API接口文档支持组件。
   * 
   * @param prop 配置组件
   * 
   * @return 返回API接口文档支持组件。
   */
  @Bean
  public Docket docket(SwaggerProperties prop) {
    Docket apiDocket = new Docket(DocumentationType.SWAGGER_2);
    ApiInfo apiInfo = new ApiInfoBuilder().title(prop.getTitle()).description(prop.getDescription())
        .version(prop.getVersion()).build();
    apiDocket.apiInfo(apiInfo);
    setDocketSecurity(apiDocket, prop);
    return apiDocket.select().apis(RequestHandlerSelectors.basePackage(prop.getBasePackage()))
        .paths(PathSelectors.any()).build();
  }

  /**
   * 配置API整合文档组件。
   * 
   * @param prop 配置组件
   * @return 返回API整合文档组件。
   */
  @Bean
  @Primary
  @ConditionalOnProperty("su.api.doc.resources[0].url")
  public SwaggerResourcesProvider provider(SwaggerProperties prop) {
    return prop::getResources;
  }

  /**
   * 配置API接口文档组件x-auth-token安全设置。
   * 
   * @param apiDocket API接口文档组件
   */
  private void setDocketSecurity(Docket apiDocket, SwaggerProperties prop) {
    ApiKey apiKey = new ApiKey("Authorization", prop.getTokenName(), "header");
    apiDocket.securitySchemes(Lists.newArrayList(apiKey));

    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    List<SecurityReference> securityReferences = Lists.newArrayList(
        new SecurityReference("Authorization", new AuthorizationScope[] {authorizationScope}));
    SecurityContext securityContext =
        SecurityContext.builder().securityReferences(securityReferences).build();
    apiDocket.securityContexts(Lists.newArrayList(securityContext));
  }
}
