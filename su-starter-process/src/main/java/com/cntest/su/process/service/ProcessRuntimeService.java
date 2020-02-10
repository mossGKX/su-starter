package com.cntest.su.process.service;

import java.util.List;
import java.util.Map;

import com.cntest.su.process.model.definition.ExtTask;
import com.cntest.su.process.model.instance.ExtProcessInstance;
import com.cntest.su.process.model.instance.ExtTaskInstance;


/**
 * 流程运行时服务接口
 * 
 * @author caining
 *
 */
public interface ProcessRuntimeService {

  /**
   * 获取指定流程指定用户组列表的所有任务节点
   * 
   * @param processInstanceId 流程实例ID
   * @param candidateGroups 所属用户组列表（admin,student）
   * @param variables 流程变量
   * @return 流程任务节点列表
   */
  public List<ExtTask> getTasks(String processInstanceId, String candidateGroups,
      Map<String, Object> variables);

  /**
   * 获取指定流程实例的当前任务实例列表
   * 
   * @param processInstanceId 流程实例ID
   * @param candidateGroups 所属用户组列表（admin,student）
   * @return 当前任务实例列表
   */
  public List<ExtTaskInstance> getCurrentTasks(String processInstanceId, String candidateGroups);

  /**
   * 通过流程定义key启动流程实例
   * 
   * @param processDefinitionKey 流程定义key
   * @param variables 流程变量
   * @param startTaskKey 起始任务定义key
   * @return
   */
  public ExtProcessInstance startProcessInstanceByKey(String processDefinitionKey,
      Map<String, Object> variables, String startTaskKey);

  /**
   * 处理当前流程任务的业务，并流转任务
   * 
   * @param processInstanceId 流程实例ID
   * @param taskDefKey 任务定义Key（如果没有传任务ID时，必须传此任务定义Key，用于定位到业务处理类并执行业务逻辑）
   * @param variables 传任务ID时才需要流程变量
   * @param candidateGroups 用户组列表
   * @param parameters 业务参数，执行业务逻辑时必须传递的参数
   * @return 返回业务处理结果
   */
  public Object submitTask(String processInstanceId, String taskDefKey,
      Map<String, Object> variables, String candidateGroups, Object parameters);

  /**
   * 跳转到指定任务节点
   * 
   * @param curTaskId 当前任务实例ID
   * @param targetFlowElementId 目标流程元素ID
   */
  public void jumpTo(String curTaskId, String targetFlowElementId);

}
