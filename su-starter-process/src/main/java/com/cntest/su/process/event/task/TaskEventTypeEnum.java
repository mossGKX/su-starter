package com.cntest.su.process.event.task;

/**
 * 任务事件类型枚举
 * 
 * @author caining
 *
 */
public enum TaskEventTypeEnum {

  /**
   * 创建事件
   */
  CREATE("create"),
  /**
   * 分配事件
   */
  ASSIGNMENT("assignment"),
  /**
   * 完成事件
   */
  COMPLETE("complete"),
  /**
   * 删除事件
   */
  DELETE("delete");

  private final String event;

  private TaskEventTypeEnum(String event) {
    this.event = event;
  }


  public String getEvent() {
    return event;
  }

}
