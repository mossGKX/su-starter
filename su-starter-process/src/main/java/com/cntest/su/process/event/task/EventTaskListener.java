package com.cntest.su.process.event.task;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.process.annotation.ProcessEventTaskListener;
import com.cntest.su.process.event.EventManager;

/**
 * 任务事件监听器抽象类
 * 
 * @author caining
 *
 */
public abstract class EventTaskListener implements InitializingBean {

  @Autowired
  EventManager eventManager;

  /**
   * 事件监听器触发函数
   * 
   * @param eventDelegateTask
   */
  public abstract void notify(EventDelegateTask eventDelegateTask);

  @Override
  public void afterPropertiesSet() throws Exception {
    ProcessEventTaskListener processEventTaskListener =
        this.getClass().getAnnotation(ProcessEventTaskListener.class);
    String namespace = processEventTaskListener.namespace();
    String processKey = processEventTaskListener.processKey();
    String taskKey = processEventTaskListener.taskKey();
    TaskEventTypeEnum event = processEventTaskListener.event();

    this.eventManager.putEventTaskListener(namespace, processKey, taskKey, event, this);
  }

}
