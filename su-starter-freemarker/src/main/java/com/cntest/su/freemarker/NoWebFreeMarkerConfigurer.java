package com.cntest.su.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 非web环境下的FreeMarker配置组件。
 */
@Slf4j
public class NoWebFreeMarkerConfigurer extends FreeMarkerConfigurationFactoryBean
    implements GenericFreeMarkerConfigurer {
  @Getter
  private ApplicationContext context;
  @Getter
  private List<AbstractFreeMarkerSettings> settings = new ArrayList<>();

  @Override
  public Configuration getConfiguration() {
    return getObject();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    context = applicationContext;
    Map<String, AbstractFreeMarkerSettings> freemarkerSettingsMap =
        context.getBeansOfType(AbstractFreeMarkerSettings.class);
    settings.addAll(freemarkerSettingsMap.values());
    Collections.sort(settings);
  }

  @Override
  public void afterPropertiesSet() throws IOException, TemplateException {
    super.afterPropertiesSet();
    init();
  }

  @Override
  protected void postProcessTemplateLoaders(List<TemplateLoader> templateLoaders) {
    super.postProcessTemplateLoaders(templateLoaders);
    for (String templatePath : getTemplatePaths()) {
      templateLoaders.add(getTemplateLoaderForPath(templatePath));
      log.debug("加载模版路径[{}]。", templatePath);
    }
  }

  @Override
  protected TemplateLoader getTemplateLoaderForPath(String templateLoaderPath) {
    if (isPreferFileSystemAccess()) {
      try {
        Resource path = getResourceLoader().getResource(templateLoaderPath);
        File file = path.getFile();
        return new FileTemplateLoader(file);
      } catch (IOException ex) {
        return new SpringTemplateLoader(getResourceLoader(), templateLoaderPath);
      }
    } else {
      return new SpringTemplateLoader(getResourceLoader(), templateLoaderPath);
    }
  }
}
