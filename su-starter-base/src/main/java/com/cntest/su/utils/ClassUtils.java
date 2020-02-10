package com.cntest.su.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import com.cntest.su.exception.SysException;

/**
 * Class工具类。
 */
public class ClassUtils {
  /**
   * 查找指定包下继承了指定类或实现了指定接口的类集合。
   * 
   * @param parentClass 父类或接口
   * @param packageNames 包名
   * @return 返回指定包下继承了指定类或实现了指定接口的类集合。
   */
  public static List<Class<?>> findClassesByParentClass(Class<?> parentClass,
      String... packageNames) {
    List<Class<?>> classes = new ArrayList<>();
    for (String className : findClassNamesByParentClass(parentClass, packageNames)) {
      try {
        classes.add(Class.forName(className));
      } catch (Exception e) {
        throw new SysException("加载[" + className + "]类时发生异常。", e);
      }
    }
    return classes;
  }

  /**
   * 查找指定包下标注了指定注解的类集合。
   * 
   * @param annotationClass 注解
   * @param packageNames 包名
   * @return 返回指定包下标注了指定注解的类集合。
   */
  public static List<Class<?>> findClassesByAnnotationClass(
      Class<? extends Annotation> annotationClass, String... packageNames) {
    List<Class<?>> classes = new ArrayList<>();
    for (String className : findClassNamesByAnnotationClass(annotationClass, packageNames)) {
      try {
        classes.add(Class.forName(className));
      } catch (Exception e) {
        throw new SysException("加载[" + className + "]类时发生异常。", e);
      }
    }
    return classes;
  }

  /**
   * 从类中获取包名。
   * 
   * @param targetClass 目标类
   * @return 返回类的包名。
   */
  public static String getPackageName(Class<?> targetClass) {
    return getPackageName(targetClass.getName());
  }

  /**
   * 从类名中获取包名。
   * 
   * @param fullClassName 完整的类名
   * @return 返回类名中的包名。
   */
  public static String getPackageName(String fullClassName) {
    return StringUtils.substringBeforeLast(fullClassName, ".");
  }

  /**
   * 查找指定包下继承了指定类或实现了指定接口的类名集合。
   * 
   * @param parentClass 父类或接口
   * @param packageNames 包名
   * @return 返回指定包下继承了指定类或实现了指定接口的类名集合。
   */
  private static List<String> findClassNamesByParentClass(Class<?> parentClass,
      String... packageNames) {
    TypeFilter filter = new AssignableTypeFilter(parentClass);
    return findClassNamesByTypeFilter(filter, packageNames);
  }

  /**
   * 查找指定包下标注了指定注解的类名集合。
   * 
   * @param annotationClass 注解
   * @param packageNames 包名
   * @return 返回指定包下标注了指定注解的类名集合。
   */
  private static List<String> findClassNamesByAnnotationClass(
      Class<? extends Annotation> annotationClass, String... packageNames) {
    TypeFilter filter = new AnnotationTypeFilter(annotationClass);
    return findClassNamesByTypeFilter(filter, packageNames);
  }

  /**
   * 查找指定包下匹配类型过滤器的类名集合。
   * 
   * @param filter 类型过滤器
   * @param packageNames 包名
   * @return 返回指定包下匹配类型过滤器的类名集合。
   */
  private static List<String> findClassNamesByTypeFilter(TypeFilter filter,
      String... packageNames) {
    List<String> classNames = new ArrayList<>();
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
    for (String packageName : packageNames) {
      try {
        String pattern = "classpath*:" + packageName.replaceAll("\\.", "/") + "/**/*.class";
        for (Resource resource : resourcePatternResolver.getResources(pattern)) {
          if (resource.isReadable()) {
            MetadataReader reader = readerFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            if (filter.match(reader, readerFactory)) {
              classNames.add(className);
            }
          }
        }
      } catch (Exception e) {
        throw new SysException("获取[" + packageName + "]包下的类名时发生异常。", e);
      }
    }
    return classNames;
  }

  /**
   * 私有构造方法。
   */
  private ClassUtils() {}
}
