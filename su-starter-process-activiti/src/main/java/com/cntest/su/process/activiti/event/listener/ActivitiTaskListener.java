package com.cntest.su.process.activiti.event.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.cntest.su.process.activiti.converter.ActivitiInstanceConverter;
import com.cntest.su.process.event.EventManager;
import com.cntest.su.process.event.task.TaskEventTypeEnum;
import com.cntest.su.process.model.instance.ExtTaskInstance;

/**
 * activiti分配事件
 * 
 * @author caining
 *
 */
public class ActivitiTaskListener implements TaskListener {

  private EventManager eventManager;

  private ActivitiInstanceConverter activitiInstanceConverter = new ActivitiInstanceConverter();

  public ActivitiTaskListener(EventManager eventManager) {
    this.eventManager = eventManager;
  }

  @Override
  public void notify(DelegateTask delegateTask) {
    // TODO Auto-generated method stub

    ExtTaskInstance extTaskInstance =
        this.activitiInstanceConverter.convertTaskInstance(delegateTask);

    // 获取相关属性
    String namespace = extTaskInstance.getExtTask().getNamespace();
    String processKey = extTaskInstance.getExtTask().getProcessKey();
    String taskKey = delegateTask.getTaskDefinitionKey();
    TaskEventTypeEnum event = null;

    if (delegateTask.getEventName().equals("create")) {
      event = TaskEventTypeEnum.CREATE;
    } else if (delegateTask.getEventName().equals("assignment")) {
      event = TaskEventTypeEnum.ASSIGNMENT;
    } else if (delegateTask.getEventName().equals("complete")) {
      event = TaskEventTypeEnum.COMPLETE;
    } else if (delegateTask.getEventName().equals("delete")) {
      event = TaskEventTypeEnum.DELETE;
    }

    if (event != null) {
      this.eventManager.notifyTaskEventListener(namespace, processKey, taskKey, event,
          extTaskInstance);
    }

  }

}
