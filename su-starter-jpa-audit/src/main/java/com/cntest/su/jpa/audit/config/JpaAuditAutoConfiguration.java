package com.cntest.su.jpa.audit.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cntest.su.auth.AuthUser;
import com.cntest.su.jpa.audit.aspect.DetailLogAspect;
import com.cntest.su.jpa.audit.aspect.SimpleLogAspect;
import com.cntest.su.jpa.audit.handle.GenericAuditorAware;
import com.cntest.su.jpa.audit.service.BizLogService;
import com.cntest.su.jpa.config.JpaAutoConfiguration;
import com.cntest.su.jpa.search.dao.FullTextDaoScan;

@Configuration
@AutoConfigureAfter(value = JpaAutoConfiguration.class)
@EnableJpaAuditing
@EntityScan("com.cntest.su.jpa.audit.entity")
@FullTextDaoScan
public class JpaAuditAutoConfiguration {
  @Bean
  public AuditorAware<AuthUser> auditorAware() {
    return new GenericAuditorAware();
  }

  @Bean
  public BizLogService bizLogService() {
    return new BizLogService();
  }

  @Bean
  public SimpleLogAspect simpleLogAspect() {
    return new SimpleLogAspect();
  }

  @Bean
  public DetailLogAspect detailLogAspect() {
    return new DetailLogAspect();
  }
}
