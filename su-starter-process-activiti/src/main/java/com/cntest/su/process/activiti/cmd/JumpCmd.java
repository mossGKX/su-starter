package com.cntest.su.process.activiti.cmd;

import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

public class JumpCmd implements Command<ExecutionEntity> {

  private String targetFlowElementId;
  private String curTaskId;

  public JumpCmd(String curTaskId, String targetFlowElementId) {
    this.curTaskId = curTaskId;
    this.targetFlowElementId = targetFlowElementId;
  }

  @Override
  public ExecutionEntity execute(CommandContext commandContext) {
    // TODO Auto-generated method stub
    ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
    TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
    // 获取当前任务的来源任务及来源节点信息
    TaskEntity taskEntity = taskEntityManager.findById(curTaskId);
    ExecutionEntity executionEntity = executionEntityManager.findById(taskEntity.getExecutionId());
    Process process = ProcessDefinitionUtil.getProcess(executionEntity.getProcessDefinitionId());
    // 删除当前节点
    taskEntityManager.deleteTask(taskEntity, "", true, true);
    // 获取要跳转的目标节点
    FlowElement targetFlowElement = process.getFlowElement(targetFlowElementId);
    executionEntity.setCurrentFlowElement(targetFlowElement);
    ActivitiEngineAgenda agenda = commandContext.getAgenda();
    agenda.planContinueProcessInCompensation(executionEntity);

    // return null;
    return executionEntity;
  }

}
