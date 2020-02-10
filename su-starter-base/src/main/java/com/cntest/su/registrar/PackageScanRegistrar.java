package com.cntest.su.registrar;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import com.cntest.su.utils.ClassUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 继承ImportBeanDefinitionRegistrar实现的用于包扫描的注册器接口。
 */
public interface PackageScanRegistrar extends ImportBeanDefinitionRegistrar {
  /**
   * 根据扫描注解查找继承了指定类或实现了指定接口的类集合。
   * 
   * @param metadata 注解元数据
   * @param scanAnnotationClass 扫描注解类
   * @param targetParentClass 目标父类
   * @return 返回符合条件的类列表。
   */
  default List<Class<?>> findClassesByParentClass(AnnotationMetadata metadata,
      Class<? extends Annotation> scanAnnotationClass, Class<?> targetParentClass) {
    Set<String> packages = getPackagesToScan(metadata, scanAnnotationClass);
    return ClassUtils.findClassesByParentClass(targetParentClass, packages.toArray(new String[0]));
  }

  /**
   * 根据扫描注解查找标注了指定注解的类集合。
   * 
   * @param metadata 注解元数据
   * @param scanAnnotationClass 扫描注解类
   * @param targetAnnotationClass 目标注解类
   * @return 返回符合条件的类列表。
   */
  default List<Class<?>> findClassesByAnnotationClass(AnnotationMetadata metadata,
      Class<? extends Annotation> scanAnnotationClass,
      Class<? extends Annotation> targetAnnotationClass) {
    Set<String> packages = getPackagesToScan(metadata, scanAnnotationClass);
    return ClassUtils.findClassesByAnnotationClass(targetAnnotationClass,
        packages.toArray(new String[0]));
  }

  /**
   * 获取扫描包名集合。
   * 
   * @param metadata 注解元数据
   * @param scanAnnotationClass 扫描注解类
   * @return 返回扫描包名集合。
   */
  default Set<String> getPackagesToScan(AnnotationMetadata metadata,
      Class<? extends Annotation> scanAnnotationClass) {
    Set<String> packagesToScan = new LinkedHashSet<>();

    AnnotationAttributes attributes = AnnotationAttributes
        .fromMap(metadata.getAnnotationAttributes(scanAnnotationClass.getName()));
    packagesToScan.addAll(Arrays.asList(attributes.getStringArray("basePackages")));

    for (Class<?> basePackageClass : attributes.getClassArray("basePackageClasses")) {
      packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
    }

    if (packagesToScan.isEmpty()) {
      String packageName = ClassUtils.getPackageName(metadata.getClassName());
      Assert.state(!StringUtils.isEmpty(packageName), scanAnnotationClass.getName() + "不能应用于默认包。");
      return Collections.singleton(packageName);
    }

    return packagesToScan;
  }
}
