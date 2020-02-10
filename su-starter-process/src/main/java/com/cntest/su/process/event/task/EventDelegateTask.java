package com.cntest.su.process.event.task;

import com.cntest.su.process.model.instance.ExtTaskInstance;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务的事件代理类
 * 
 * @author caining
 *
 */
public class EventDelegateTask {

  /**
   * 事件
   */
  @Getter
  @Setter
  private String event;

  /**
   * 任务实例
   */
  @Getter
  @Setter
  private ExtTaskInstance extTaskInstance;

}
