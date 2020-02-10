package com.cntest.su.process.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface ProcessServiceProcessor {

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

}
