package com.cntest.su.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cntest.su.jpa.cache.EntityCacheManager;
import com.cntest.su.jpa.dao.DaoUtils;
import com.cntest.su.jpa.entity.IdEntityToString;
import com.cntest.su.jpa.entity.StringToIdEntity;
import com.cntest.su.jpa.entity.StringToUuidEntity;
import com.cntest.su.jpa.entity.UuidEntityToString;

@Configuration
@AutoConfigureAfter(value = JpaRepositoriesAutoConfiguration.class)
@EntityScan({"com.cntest.su.jpa.converter", "com.cntest.su.jpa.usertype"})
public class JpaAutoConfiguration implements WebMvcConfigurer {
  @Bean
  EntityCacheManager entityCacheManager() {
    return new EntityCacheManager();
  }

  /**
   * 当开启了查询缓存时，构建查询缓存配置。
   * 
   * @return 返回查询缓存配置。
   */
  @Bean("com.cntest.su.jpa.config.CacheSettings")
  CacheSettings cacheSettings() {
    return new CacheSettings();
  }

  @Bean
  DaoUtils daoUtils() {
    return new DaoUtils();
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverterFactory(new StringToUuidEntity());
    registry.addConverter(new UuidEntityToString());
    registry.addConverterFactory(new StringToIdEntity());
    registry.addConverter(new IdEntityToString());
  }
}
