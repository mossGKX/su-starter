package com.cntest.su.freemarker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cntest.su.exception.SysException;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * 标准FreeMarker配置接口，支持模块化加载模版路径以及扩展配置。
 */
public interface GenericFreeMarkerConfigurer extends ApplicationContextAware {
  Configuration getConfiguration();

  ApplicationContext getContext();

  List<AbstractFreeMarkerSettings> getSettings();

  /**
   * 初始化。
   * 
   * @throws TemplateModelException 初始化失败时抛出异常
   */
  default void init() throws TemplateModelException {
    for (AbstractFreeMarkerSettings setting : getSettings()) {
      initEnums(setting.getEnumClasses());
      initStatics(setting.getStaticClasses());
      initSharedVariables(setting.getGlobalBeans());
      initAutoIncludes(setting.getAutoIncludes());
      initAutoImports(setting.getAutoImports());
    }
  }

  /**
   * 初始化枚举变量。
   * 
   * @param enumClasses 枚举类列表
   * @throws TemplateModelException 初始化枚举变量失败时抛出异常
   */
  default void initEnums(List<Class<?>> enumClasses) throws TemplateModelException {
    TemplateHashModel enums =
        ((BeansWrapper) getConfiguration().getObjectWrapper()).getEnumModels();
    for (Class<?> enumClass : enumClasses) {
      if (getConfiguration().getSharedVariableNames().contains(enumClass.getSimpleName())) {
        throw new SysException("加载枚举类[" + enumClass + "]时发生冲突。");
      }
      getConfiguration().setSharedVariable(enumClass.getSimpleName(),
          enums.get(enumClass.getName()));
    }
  }

  /**
   * 初始化静态变量。
   * 
   * @param staticClasses 静态类列表
   * @throws TemplateModelException 初始化静态变量失败时抛出异常。
   */
  default void initStatics(List<Class<?>> staticClasses) throws TemplateModelException {
    TemplateHashModel statics =
        ((BeansWrapper) getConfiguration().getObjectWrapper()).getStaticModels();
    for (Class<?> staticClass : staticClasses) {
      if (getConfiguration().getSharedVariableNames().contains(staticClass.getSimpleName())) {
        throw new SysException("加载静态类[" + staticClass + "]时发生冲突。");
      }
      getConfiguration().setSharedVariable(staticClass.getSimpleName(),
          statics.get(staticClass.getName()));
    }
  }

  /**
   * 初始化公共变量。
   * 
   * @param globalBeans 公共变量列表
   * @throws TemplateModelException 初始化公共变量失败时抛出异常。
   */
  default void initSharedVariables(Map<String, String> globalBeans) throws TemplateModelException {
    for (Entry<String, String> globalBean : globalBeans.entrySet()) {
      if (getConfiguration().getSharedVariableNames().contains(globalBean.getKey())) {
        throw new SysException(
            "加载全局组件变量[" + globalBean.getKey() + ":" + globalBean.getValue() + "]时发生冲突。");
      }
      getConfiguration().setSharedVariable(globalBean.getKey(),
          getContext().getBean(globalBean.getValue()));
    }
  }

  /**
   * 初始化自动包含文件。
   * 
   * @param autoIncludes 自动包含文件列表
   */
  default void initAutoIncludes(List<String> autoIncludes) {
    for (String autoInclude : autoIncludes) {
      if (getConfiguration().getAutoIncludes().contains(autoInclude)) {
        throw new SysException("加载自动包含文件[" + autoInclude + "]时发生冲突。");
      }
      getConfiguration().addAutoInclude(autoInclude);
    }
  }

  /**
   * 初始化自动导入文件。
   * 
   * @param autoImports 自动导入文件列表
   */
  default void initAutoImports(Map<String, String> autoImports) {
    for (Entry<String, String> autoImport : autoImports.entrySet()) {
      if (getConfiguration().getAutoImports().containsKey(autoImport.getKey())) {
        throw new SysException(
            "加载自动导入文件[" + autoImport.getKey() + ":" + autoImport.getValue() + "]时发生冲突。");
      }
      getConfiguration().addAutoImport(autoImport.getKey(), autoImport.getValue());
    }
  }

  /**
   * 获取模版路径列表。
   * 
   * @return 返回模版路径列表。
   */
  default List<String> getTemplatePaths() {
    List<String> templatePaths = new ArrayList<>();
    for (AbstractFreeMarkerSettings settings : getSettings()) {
      for (String templatePath : settings.getTemplatePaths()) {
        if (templatePaths.contains(templatePath)) {
          throw new SysException("加载模版路径[" + templatePath + "]时发生冲突。");
        }
        templatePaths.add(templatePath);
      }
    }
    return templatePaths;
  }
}
