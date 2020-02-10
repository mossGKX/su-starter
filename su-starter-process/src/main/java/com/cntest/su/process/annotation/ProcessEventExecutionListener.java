package com.cntest.su.process.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.cntest.su.process.event.execution.ExecutionEventTypeEnum;

/**
 * 执行监听器注解
 * 
 * @author caining
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface ProcessEventExecutionListener {
  /**
   * 命名空间
   * 
   * @return 命名空间
   */
  String namespace();

  /**
   * 流程定义key
   * 
   * @return 流程定义key
   */
  String processKey();

  /**
   * 流程元素定义对象id
   * 
   * @return 流程元素定义对象id
   */
  String flowElementId();

  /**
   * 事件
   * 
   * @return 事件
   */
  ExecutionEventTypeEnum event();
}
