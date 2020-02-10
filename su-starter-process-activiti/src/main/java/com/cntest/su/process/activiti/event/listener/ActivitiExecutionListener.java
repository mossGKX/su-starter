package com.cntest.su.process.activiti.event.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.cntest.su.process.activiti.converter.ActivitiDefinitionConverter;
import com.cntest.su.process.event.EventManager;
import com.cntest.su.process.event.execution.EventDelegateExecution;
import com.cntest.su.process.event.execution.ExecutionEventTypeEnum;
import com.cntest.su.process.model.definition.ExtFlowElement;

public class ActivitiExecutionListener implements ExecutionListener {

  private EventManager eventManager;

  private ActivitiDefinitionConverter activitiDefinitionConverter =
      new ActivitiDefinitionConverter();

  public ActivitiExecutionListener(EventManager eventManager) {
    this.eventManager = eventManager;
  }

  @Override
  public void notify(DelegateExecution execution) {
    // TODO Auto-generated method stub
    ExtFlowElement extFlowElement =
        this.activitiDefinitionConverter.convertFrom(execution.getCurrentFlowElement());
    String namespace = extFlowElement.getNamespace();
    String processKey = extFlowElement.getProcessKey();
    String flowElementId = extFlowElement.getId();
    ExecutionEventTypeEnum event = null;

    if (execution.getEventName().equals("start")) {
      event = ExecutionEventTypeEnum.START;
    } else if (execution.getEventName().equals("end")) {
      event = ExecutionEventTypeEnum.END;
    }


    if (event != null) {
      EventDelegateExecution eventDelegateExecution = new EventDelegateExecution();
      eventDelegateExecution.setEvent(event.getEvent());
      eventDelegateExecution.setProcessInstanceId(execution.getProcessInstanceId());
      eventDelegateExecution.setExtFlowElement(extFlowElement);
      eventDelegateExecution.setVariables(execution.getVariables());
      this.eventManager.notifyExecutionEventListener(namespace, processKey, flowElementId, event,
          eventDelegateExecution);
    }
  }

}
