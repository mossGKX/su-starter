package com.cntest.su.freemarker.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FreeMarker自动配置，支持非web环境和web环境。
 */
@Configuration
@EnableConfigurationProperties(FreeMarkerProperties.class)
@Import({NoWebFreeMarkerAutoConfiguration.class, WebFreeMarkerAutoConfiguration.class})
public class FreeMarkerAutoConfiguration {

}
