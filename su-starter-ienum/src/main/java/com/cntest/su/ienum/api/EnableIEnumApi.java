package com.cntest.su.ienum.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

/**
 * 该组件用于触发自动生成Dao组件，该组件必须和 @see EntityScan 注解一起使用。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({IEnumApiConfiguration.class, IEnumManagerRegistrar.class})
public @interface EnableIEnumApi {
  @AliasFor("basePackages")
  String[] value() default {};

  @AliasFor("value")
  String[] basePackages() default {};

  Class<?>[] basePackageClasses() default {};
}
