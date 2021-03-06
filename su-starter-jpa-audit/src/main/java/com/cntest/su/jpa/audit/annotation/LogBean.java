package com.cntest.su.jpa.audit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 高级日志关联对象注解。
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogBean {
  /**
   * 日志字段数组。
   * 
   * @return 返回日志字段数组。
   */
  LogField[] value();
}
