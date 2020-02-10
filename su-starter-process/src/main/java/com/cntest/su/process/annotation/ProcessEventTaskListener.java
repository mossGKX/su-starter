package com.cntest.su.process.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.cntest.su.process.event.task.TaskEventTypeEnum;

/**
 * 任务事件监听器注解
 * 
 * @author caining
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface ProcessEventTaskListener {

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
   * 任务定义key
   * 
   * @return 任务定义key
   */
  String taskKey();

  /**
   * 事件
   * 
   * @return 事件
   */
  TaskEventTypeEnum event();
}
