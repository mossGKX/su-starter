package com.cntest.su.process.event;

import java.util.HashMap;
import java.util.Map;

import com.cntest.su.process.event.execution.EventDelegateExecution;
import com.cntest.su.process.event.execution.EventExecutionListener;
import com.cntest.su.process.event.execution.ExecutionEventTypeEnum;
import com.cntest.su.process.event.task.EventDelegateTask;
import com.cntest.su.process.event.task.EventTaskListener;
import com.cntest.su.process.event.task.TaskEventTypeEnum;
import com.cntest.su.process.model.instance.ExtTaskInstance;

public class EventManager {

  private Map<String, Map<String, EventTaskListener>> taskEventListenerMap =
      new HashMap<String, Map<String, EventTaskListener>>();

  private Map<String, Map<String, EventExecutionListener>> executionEventListenerMap =
      new HashMap<String, Map<String, EventExecutionListener>>();

  public void notifyTaskEventListener(String namespace, String processKey, String taskKey,
      TaskEventTypeEnum taskEventTypeEnum, ExtTaskInstance extTaskInstance) {

    // 获取指定命名空间、流程、任务的事件监听器
    String key = this.taskEventKey(namespace, processKey, taskKey);
    if (this.taskEventListenerMap.containsKey(key)) {
      Map<String, EventTaskListener> taskEvListenerMap = this.taskEventListenerMap.get(key);
      if (taskEvListenerMap.containsKey(taskEventTypeEnum.getEvent())) {
        EventTaskListener taskEventListener = taskEvListenerMap.get(taskEventTypeEnum.getEvent());

        // 构造EventDelegateTask对象
        EventDelegateTask eventDelegateTask = new EventDelegateTask();
        eventDelegateTask.setEvent(taskEventTypeEnum.getEvent());
        eventDelegateTask.setExtTaskInstance(extTaskInstance);

        // 触发事件
        taskEventListener.notify(eventDelegateTask);
      }
    }

  }

  public void notifyExecutionEventListener(String namespace, String processKey,
      String flowElementId, ExecutionEventTypeEnum executionEventTypeEnum,
      EventDelegateExecution eventDelegateExecution) {

    String key = this.executionEventKey(namespace, processKey, flowElementId);
    if (this.executionEventListenerMap.containsKey(key)) {
      Map<String, EventExecutionListener> executionEvListenerMap =
          this.executionEventListenerMap.get(key);
      if (executionEvListenerMap.containsKey(executionEventTypeEnum.getEvent())) {
        EventExecutionListener eventExecutionListener =
            executionEvListenerMap.get(executionEventTypeEnum.getEvent());

        eventExecutionListener.notify(eventDelegateExecution);
      }
    }

  }


  public void putEventTaskListener(String namespace, String processKey, String taskKey,
      TaskEventTypeEnum taskEventTypeEnum, EventTaskListener taskEventListener) {

    String key = this.taskEventKey(namespace, processKey, taskKey);
    Map<String, EventTaskListener> taskEvListenerMap = null;

    if (!this.taskEventListenerMap.containsKey(key)) {
      taskEvListenerMap = new HashMap<String, EventTaskListener>();
      this.taskEventListenerMap.put(key, taskEvListenerMap);
    } else {
      taskEvListenerMap = this.taskEventListenerMap.get(key);
    }

    taskEvListenerMap.put(taskEventTypeEnum.getEvent(), taskEventListener);

  }

  public void putEventExecutionListener(String namespace, String processKey, String flowElementId,
      ExecutionEventTypeEnum executionEventTypeEnum,
      EventExecutionListener eventExecutionListener) {
    String key = this.executionEventKey(namespace, processKey, flowElementId);
    Map<String, EventExecutionListener> executionEvListenerMap = null;

    if (!this.executionEventListenerMap.containsKey(key)) {
      executionEvListenerMap = new HashMap<String, EventExecutionListener>();
      this.executionEventListenerMap.put(key, executionEvListenerMap);
    } else {
      executionEvListenerMap = this.executionEventListenerMap.get(key);
    }

    executionEvListenerMap.put(executionEventTypeEnum.getEvent(), eventExecutionListener);
  }

  private String executionEventKey(String namespace, String processKey, String flowElementId) {
    return namespace + ":" + processKey + ":" + flowElementId;
  }

  private String taskEventKey(String namespace, String processKey, String taskKey) {
    return namespace + ":" + processKey + ":" + taskKey;
  }

}
