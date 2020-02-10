package com.cntest.su.process.activiti.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ExpressionFactory;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.process.activiti.cmd.JumpCmd;
import com.cntest.su.process.activiti.converter.ActivitiDefinitionConverter;
import com.cntest.su.process.model.definition.ExtFlowElement;
import com.cntest.su.process.model.definition.ExtProcess;
import com.cntest.su.process.model.definition.ExtTask;
import com.cntest.su.process.model.instance.ExtProcessInstance;
import com.cntest.su.process.model.instance.ExtTaskInstance;
import com.cntest.su.process.processor.ServiceProcessorManager;
import com.cntest.su.process.service.ProcessRuntimeService;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

/**
 * 流程运行服务接口实现
 * 
 * @author caining
 *
 */
public class ProcessRuntimeServiceImpl implements ProcessRuntimeService {

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private HistoryService historyService;

  @Autowired
  private ServiceProcessorManager serviceProcessorManager;

  @Autowired
  private ManagementService managementService;

  /**
   * activiti流程元素定义对象转换器
   */
  private ActivitiDefinitionConverter activitiDefinitionConverter =
      new ActivitiDefinitionConverter();

  @Override
  public List<ExtTask> getTasks(String processInstanceId, String candidateGroups,
      Map<String, Object> variables) {

    List<ExtTask> userTasks = new ArrayList<ExtTask>();

    /*
     * ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
     * .processInstanceId(processInstanceId).includeProcessVariables().singleResult();
     */
    HistoricProcessInstance historicProcessInstance =
        this.historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(processInstanceId).includeProcessVariables().singleResult();
    // 获取流程变量并合并入variables参数中
    /* Map<String, Object> vars = processInstance.getProcessVariables(); */
    Map<String, Object> vars = historicProcessInstance.getProcessVariables();
    if (variables == null) {
      variables = new HashMap<String, Object>();
    }
    for (String key : vars.keySet()) {
      if (!variables.containsKey(key)) {
        variables.put(key, vars.get(key));
      }
    }

    // 获取流程定义的元素列表
    /* String processDefId = processInstance.getProcessDefinitionId(); */
    String processDefId = historicProcessInstance.getProcessDefinitionId();
    BpmnModel bpmnModel = this.repositoryService.getBpmnModel(processDefId);
    Process process = bpmnModel.getProcesses().get(0);
    Collection<FlowElement> flowElements = process.getFlowElements();

    // 解析变量并传入
    ExpressionFactory factory = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    if (variables != null) {
      Object varValue = null;
      for (String varkey : variables.keySet()) {
        varValue = variables.get(varkey);
        if (varValue == null)
          break;
        Class<? extends Object> clas = varValue.getClass();
        context.setVariable(varkey, factory.createValueExpression(varValue, clas));
      }
    }

    for (FlowElement flowElement : flowElements) {
      if (flowElement instanceof UserTask) {
        UserTask userTask = (UserTask) flowElement;
        /**
         * 1、获取用户任务节点的进入SequenceFlow 2、判断是否有条件表达式 3、根据变量判断条件表达式，用户任务节点是否满足条件，满足条件的取出来
         */
        List<SequenceFlow> sequenceFlows = userTask.getIncomingFlows();
        SequenceFlow sequenceFlow = null;
        boolean isCondition = true;
        for (int i = 0; i < sequenceFlows.size(); ++i) {
          sequenceFlow = sequenceFlows.get(i);
          String conditionExpression = sequenceFlow.getConditionExpression();
          if (conditionExpression != null) {
            ValueExpression e =
                factory.createValueExpression(context, conditionExpression, boolean.class);
            try {
              isCondition = (boolean) e.getValue(context);
            } catch (PropertyNotFoundException ex) {
              // 满足条件
            }
          }
        }
        String[] cGroups = candidateGroups.split(",");
        List<String> canGroups = userTask.getCandidateGroups();
        String candidateGroup = null;
        ExtTask extTask = null;
        for (int i = 0; i < cGroups.length; ++i) {
          candidateGroup = cGroups[i];
          if (canGroups.contains(candidateGroup) && isCondition) {
            extTask = (ExtTask) this.activitiDefinitionConverter.convertFrom(userTask);
            userTasks.add(extTask);
          }
        }
      }
    }
    return userTasks;
  }

  @Override
  public List<ExtTaskInstance> getCurrentTasks(String processInstanceId, String candidateGroups) {

    List<Task> actTasks = this.getCurrentTasksActiviti(processInstanceId, candidateGroups);
    List<ExtTaskInstance> tasks = null;
    Task task = null;
    ExtTaskInstance extTaskInstance = null;
    if (actTasks != null) {
      tasks = new ArrayList<ExtTaskInstance>();
      for (int i = 0; i < actTasks.size(); ++i) {
        task = actTasks.get(i);
        extTaskInstance = this.convertTaskFrom(task);
        tasks.add(extTaskInstance);
      }
    }

    return tasks;
  }

  private List<Task> getCurrentTasksActiviti(String processInstanceId, String candidateGroups) {
    TaskQuery taskQuery = this.taskService.createTaskQuery().processInstanceId(processInstanceId);
    if (candidateGroups != null) {
      String[] canGroups = candidateGroups.split(",");
      String candidateGroup = null;
      for (int i = 0; i < canGroups.length; ++i) {
        candidateGroup = canGroups[i];
        taskQuery.taskCandidateGroup(candidateGroup);
      }
    }
    List<Task> actTasks = taskQuery.list();
    return actTasks;
  }

  @Override
  public ExtProcessInstance startProcessInstanceByKey(String processDefinitionKey,
      Map<String, Object> variables, String startTaskKey) {

    ProcessInstance processInstance =
        this.runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    if (!StringUtils.isEmpty(startTaskKey)) {
      this.recursionComplete(processInstance.getId(), variables, startTaskKey);
    }
    ExtProcessInstance extProcessInstance = this.convertProcessInstanceFrom(processInstance);

    return extProcessInstance;
  }


  private void recursionComplete(String processInstanceId, Map<String, Object> variables,
      String startTaskKey) {
    List<Task> tasks =
        this.taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    Task task = null;
    if (tasks != null) {
      for (int i = 0; i < tasks.size(); ++i) {
        task = tasks.get(i);
        if (task.getTaskDefinitionKey().equals(startTaskKey)) {
          return;
        }
        this.taskService.complete(task.getId(), variables);
      }
      this.recursionComplete(processInstanceId, variables, startTaskKey);
    }
  }

  @Override
  public Object submitTask(String processInstanceId, String taskDefKey,
      Map<String, Object> variables, String candidateGroups, Object parameters) {

    Object result = null;
    /**
     * 执行业务处理
     */
    boolean canCompleteTask = false;
    Process process = this.getProcess(processInstanceId);
    if (process != null) {
      ExtProcess extProcess = this.activitiDefinitionConverter.convertFrom(process);
      String namespace = extProcess.getNamespace();
      result = this.serviceProcessorManager.execute(namespace, extProcess.getId(), taskDefKey,
          parameters);
    }

    if (result != null) {
      canCompleteTask = true;
    }

    // 如果业务说明不能完成任务，那么直接返回业务处理结果
    if (canCompleteTask == false)
      return result;

    // 执行流程
    List<Task> curTasks = this.getCurrentTasksActiviti(processInstanceId, null);
    if ((!StringUtils.isEmpty(taskDefKey)) && (curTasks != null)) {
      Task curTask = null;
      Task task = null;
      for (int i = 0; i < curTasks.size(); ++i) {
        task = curTasks.get(i);
        if (task.getTaskDefinitionKey().equals(taskDefKey)) {
          curTask = task;
          break;
        }
      }

      if (curTask != null) {
        this.taskService.complete(curTask.getId(), variables);
      } else {
        for (int i = 0; i < curTasks.size(); ++i) {
          task = curTasks.get(i);
          this.jumpTo(task.getId(), taskDefKey);
        }
        this.execSubmitTask(processInstanceId, taskDefKey, variables);
      }
    }



    return result;
  }


  private void execSubmitTask(String processInstanceId, String taskDefKey,
      Map<String, Object> variables) {
    /**
     * 执行流程流转
     */
    List<Task> curTasks = this.getCurrentTasksActiviti(processInstanceId, null);

    if ((!StringUtils.isEmpty(taskDefKey)) && (curTasks != null)) {
      Task curTask = null;
      Task task = null;
      for (int i = 0; i < curTasks.size(); ++i) {
        task = curTasks.get(i);
        if (task.getTaskDefinitionKey().equals(taskDefKey)) {
          curTask = task;
          break;
        }
      }

      if (curTask != null) {
        this.taskService.complete(curTask.getId(), variables);
      }
    }
  }


  /**
   * 获取指定流程实例指定任务定义key的任务节点元素
   * 
   * @param processInstanceId
   * @param taskDefKey
   * @return
   */
  private FlowElement getFlowElement(String processInstanceId, String taskDefKey) {

    FlowElement flowElement = null;

    Process process = this.getProcess(processInstanceId);
    if (process != null) {
      Map<String, FlowElement> flowElementMap = process.getFlowElementMap();
      flowElement = flowElementMap.get(taskDefKey);
    }

    return flowElement;
  }

  /**
   * 获取流程实例的流程定义对象
   * 
   * @param processInstanceId
   * @return
   */
  private Process getProcess(String processInstanceId) {

    Process process = null;

    HistoricProcessInstance historicProcessInstance =
        this.historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(processInstanceId).includeProcessVariables().singleResult();
    String processDefinitionId = historicProcessInstance.getProcessDefinitionId();

    BpmnModel model = this.repositoryService.getBpmnModel(processDefinitionId);
    if (model != null) {
      process = model.getMainProcess();
    }

    return process;
  }


  /**
   * 转换 activiti任务实例 为 内部扩展任务实例
   * 
   * @param task activiti任务实例
   * @return 内部扩展任务实例
   */
  private ExtTaskInstance convertTaskFrom(Task task) {

    String id = task.getId();

    /**
     * 构建 内部扩展任务实例对象
     */
    ExtTaskInstance extTaskInstance = new ExtTaskInstance();
    extTaskInstance.setId(id);
    extTaskInstance.setProcessInstanceId(task.getProcessInstanceId());

    /**
     * 构造 内部扩展任务定义对象
     */

    FlowElement flowElement =
        this.getFlowElement(task.getProcessInstanceId(), task.getTaskDefinitionKey());
    if (flowElement != null) {
      UserTask userTask = (UserTask) flowElement;

      ExtFlowElement extFlowElement = this.activitiDefinitionConverter.convertFrom(userTask);
      ExtTask extTask = (ExtTask) extFlowElement;
      extTaskInstance.setExtTask(extTask);
    }

    return extTaskInstance;

  }


  private ExtProcessInstance convertProcessInstanceFrom(ProcessInstance processInstance) {

    String id = processInstance.getId();

    /**
     * 构造 内部扩展流程实例对象
     */
    ExtProcessInstance extProcessInstance = new ExtProcessInstance();
    extProcessInstance.setId(id);

    /**
     * 构造 内部扩展流程定义对象
     */
    String processDefinitionId = processInstance.getProcessDefinitionId();
    BpmnModel model = this.repositoryService.getBpmnModel(processDefinitionId);

    if (model != null) {
      ExtProcess extProcess = this.activitiDefinitionConverter.convertFrom(model.getMainProcess());
      extProcessInstance.setExtProcess(extProcess);
    }
    return extProcessInstance;

  }

  @Override
  public void jumpTo(String curTaskId, String targetFlowElementId) {
    // TODO Auto-generated method stub
    this.managementService.executeCommand(new JumpCmd(curTaskId, targetFlowElementId));
  }

}
