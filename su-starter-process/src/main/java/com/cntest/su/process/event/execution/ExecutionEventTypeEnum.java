package com.cntest.su.process.event.execution;

/**
 * 执行事件类型枚举
 * 
 * @author caining
 *
 */
public enum ExecutionEventTypeEnum {

  /**
   * 开始事件
   */
  START("start"),
  /**
   * 结束事件
   */
  END("end");


  private final String event;

  private ExecutionEventTypeEnum(String event) {
    this.event = event;
  }


  public String getEvent() {
    return event;
  }

}
