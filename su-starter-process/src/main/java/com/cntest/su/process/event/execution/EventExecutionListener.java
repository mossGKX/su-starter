package com.cntest.su.process.event.execution;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.process.annotation.ProcessEventExecutionListener;
import com.cntest.su.process.event.EventManager;

public abstract class EventExecutionListener implements InitializingBean {

  @Autowired
  EventManager eventManager;

  /**
   * 事件监听器触发函数
   * 
   * @param eventDelegateTask
   */
  public abstract void notify(EventDelegateExecution eventDelegateExecution);

  @Override
  public void afterPropertiesSet() throws Exception {
    ProcessEventExecutionListener processEventExecutionListener =
        this.getClass().getAnnotation(ProcessEventExecutionListener.class);
    String namespace = processEventExecutionListener.namespace();
    String processKey = processEventExecutionListener.processKey();
    String flowElementId = processEventExecutionListener.flowElementId();
    ExecutionEventTypeEnum event = processEventExecutionListener.event();

    this.eventManager.putEventExecutionListener(namespace, processKey, flowElementId, event, this);
  }
}
