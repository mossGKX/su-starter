package com.cntest.su.process.activiti.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cntest.su.process.model.definition.ExtEndEvent;
import com.cntest.su.process.model.definition.ExtExclusiveGateway;
import com.cntest.su.process.model.definition.ExtFlowElement;
import com.cntest.su.process.model.definition.ExtProcess;
import com.cntest.su.process.model.definition.ExtSequenceFlow;
import com.cntest.su.process.model.definition.ExtStartEvent;
import com.cntest.su.process.model.definition.ExtUserTask;
import com.cntest.su.utils.BeanUtils;

/**
 * activiti流程元素对象转换器
 * 
 * @author caining
 *
 */
public class ActivitiDefinitionConverter {

  /**
   * 转换 内部扩展流程元素定义对象 为 activiti流程元素定义对象
   * 
   * @param extFlowElement 内部扩展流程元素定义对象
   * @return activiti流程元素定义对象
   */
  public FlowElement convertTo(ExtFlowElement extFlowElement) {
    FlowElement flowElement = null;

    if (extFlowElement instanceof ExtUserTask) {

      ExtUserTask extUserTask = (ExtUserTask) extFlowElement;
      flowElement = this.convertTo(extUserTask);

    } else if (extFlowElement instanceof ExtStartEvent) {

      ExtStartEvent extStartEvent = (ExtStartEvent) extFlowElement;
      flowElement = this.convertTo(extStartEvent);

    } else if (extFlowElement instanceof ExtSequenceFlow) {

      ExtSequenceFlow extSequenceFlow = (ExtSequenceFlow) extFlowElement;
      flowElement = this.convertTo(extSequenceFlow);

    } else if (extFlowElement instanceof ExtExclusiveGateway) {

      ExtExclusiveGateway extExclusiveGateway = (ExtExclusiveGateway) extFlowElement;
      flowElement = this.convertTo(extExclusiveGateway);

    } else if (extFlowElement instanceof ExtEndEvent) {

      ExtEndEvent extEndEvent = (ExtEndEvent) extFlowElement;
      flowElement = this.convertTo(extEndEvent);

    }


    return flowElement;
  }

  /**
   * 转换 内部扩展流程定义对象 为 activiti流程定义对象
   * 
   * @param extProcess 内部扩展流程定义对象
   * @return activiti流程定义对象
   */
  public Process converto(ExtProcess extProcess) {

    String id = extProcess.getId();
    String name = extProcess.getName();

    ExtProcess svExtProcess = new ExtProcess();
    BeanUtils.copyFields(extProcess, svExtProcess, "flowElementList,flowElementMap");

    /**
     * 构建activiti流程定义对象
     */
    Process process = new Process();
    process.setId(id);
    process.setName(name);
    String extProcessStr = JSON.toJSONString(svExtProcess);
    process.setDocumentation(extProcessStr);

    return process;
  }

  /**
   * 转换 内部扩展流程元素定义对象 为 activiti流程元素定义对象
   * 
   * @param flowElement activiti流程元素定义对象
   * @return 内部扩展流程元素定义对象
   */
  public ExtFlowElement convertFrom(FlowElement flowElement) {
    ExtFlowElement extFlowElement = null;

    if (flowElement instanceof UserTask) {

      UserTask userTask = (UserTask) flowElement;
      extFlowElement = this.convertFrom(userTask);

    } else if (flowElement instanceof StartEvent) {

      StartEvent startEvent = (StartEvent) flowElement;
      extFlowElement = this.convertFrom(startEvent);

    } else if (flowElement instanceof SequenceFlow) {

      SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
      extFlowElement = this.convertFrom(sequenceFlow);

    } else if (flowElement instanceof ExclusiveGateway) {

      ExclusiveGateway exclusiveGateway = (ExclusiveGateway) flowElement;
      extFlowElement = this.convertFrom(exclusiveGateway);

    } else if (flowElement instanceof EndEvent) {

      EndEvent endEvent = (EndEvent) flowElement;
      extFlowElement = this.convertFrom(endEvent);

    }

    return extFlowElement;
  }

  /**
   * 转换 activiti流程定义对象 为 内部扩展流程定义对象
   * 
   * @param process activiti流程定义对象
   * @return 内部扩展流程定义对象
   */
  public ExtProcess convertFrom(Process process) {
    String id = process.getId();
    String name = process.getName();
    String namespace = null;
    String processKey = null;

    String extProcessStr = process.getDocumentation();
    ExtProcess eProcess = JSON.parseObject(extProcessStr, ExtProcess.class);
    namespace = eProcess.getNamespace();
    processKey = eProcess.getProcessKey();

    ExtProcess extProcess = new ExtProcess();
    extProcess.setId(id);
    extProcess.setName(name);
    extProcess.setNamespace(namespace);
    extProcess.setProcessKey(processKey);

    Collection<FlowElement> flowElements = process.getFlowElements();
    FlowElement flowElement = null;

    Iterator<FlowElement> it = flowElements.iterator();
    while (it.hasNext()) {
      flowElement = it.next();
      extProcess.addFlowElement(this.convertFrom(flowElement));
    }



    return extProcess;
  }

  /**
   * 转换 activiti用户任务定义对象 为 用户任务定义对象
   * 
   * @param userTask
   * @return
   */
  private ExtUserTask convertFrom(UserTask userTask) {
    String id = userTask.getId();
    String name = userTask.getName();
    String candidateGroups = null;
    String functionName = null;
    Map<String, String> forms = null;
    String namespace = null;
    String processKey = null;

    /**
     * 转换用户组列表
     */
    List<String> canGroups = userTask.getCandidateGroups();
    StringBuilder sb = new StringBuilder();
    String candidateGroup = null;
    for (int i = 0; i < canGroups.size(); ++i) {
      candidateGroup = canGroups.get(i);
      sb.append(candidateGroup);
    }
    candidateGroups = sb.toString();

    /**
     * 将Documentation存储的json转换为ExtUserTask，获取相关信息
     */
    String userTaskJsonStr = userTask.getDocumentation();
    ExtUserTask exUserTask = JSON.parseObject(userTaskJsonStr, ExtUserTask.class);
    functionName = exUserTask.getFunctionName();
    forms = exUserTask.getForms();
    namespace = exUserTask.getNamespace();
    processKey = exUserTask.getProcessKey();

    /**
     * 构建 内部扩展用户任务定义对象
     */
    ExtUserTask extUserTask = new ExtUserTask();
    extUserTask.setId(id);
    extUserTask.setName(name);
    extUserTask.setCandidateGroups(candidateGroups);
    extUserTask.setFunctionName(functionName);
    extUserTask.setForms(forms);
    extUserTask.setNamespace(namespace);
    extUserTask.setProcessKey(processKey);

    return extUserTask;
  }

  /**
   * 转换 activiti开始节点定义对象 为 开始节点定义对象
   * 
   * @param startEvent activiti开始节点定义对象
   * @return 开始节点定义对象
   */
  private ExtStartEvent convertFrom(StartEvent startEvent) {
    String id = startEvent.getId();
    String name = startEvent.getName();
    String namespace = null;
    String processKey = null;

    String jsonStr = startEvent.getDocumentation();
    ExtStartEvent exStartEvent = JSON.parseObject(jsonStr, ExtStartEvent.class);
    namespace = exStartEvent.getNamespace();
    processKey = exStartEvent.getProcessKey();

    ExtStartEvent extStartEvent = new ExtStartEvent();
    extStartEvent.setId(id);
    extStartEvent.setName(name);
    extStartEvent.setNamespace(namespace);
    extStartEvent.setProcessKey(processKey);

    return extStartEvent;
  }

  /**
   * 转换 activiti连线定义对象 为 内部扩展连线定义对象
   * 
   * @param sequenceFlow activiti连线定义对象
   * @return 内部扩展连线定义对象
   */
  private ExtSequenceFlow convertFrom(SequenceFlow sequenceFlow) {

    String id = sequenceFlow.getId();
    String name = sequenceFlow.getName();
    String sourceRef = sequenceFlow.getSourceRef();
    String targetRef = sequenceFlow.getTargetRef();
    String conditionExpression = sequenceFlow.getConditionExpression();
    String namespace = null;
    String processKey = null;

    String jsonStr = sequenceFlow.getDocumentation();
    ExtSequenceFlow extSeqFlow = JSON.parseObject(jsonStr, ExtSequenceFlow.class);
    namespace = extSeqFlow.getNamespace();
    processKey = extSeqFlow.getProcessKey();

    ExtSequenceFlow extSequenceFlow = new ExtSequenceFlow();
    extSequenceFlow.setId(id);
    extSequenceFlow.setName(name);
    extSequenceFlow.setSourceRef(sourceRef);
    extSequenceFlow.setTargetRef(targetRef);
    extSequenceFlow.setConditionExpression(conditionExpression);
    extSequenceFlow.setNamespace(namespace);
    extSequenceFlow.setProcessKey(processKey);

    return extSequenceFlow;

  }

  /**
   * 转换 activiti排他网关对象 为 内部扩展排他网关对象
   * 
   * @param exclusiveGateway activiti排他网关对象
   * @return 内部扩展排他网关对象
   */
  private ExtExclusiveGateway convertFrom(ExclusiveGateway exclusiveGateway) {
    String id = exclusiveGateway.getId();
    String name = exclusiveGateway.getName();
    String namespace = null;
    String processKey = null;

    String jsonStr = exclusiveGateway.getDocumentation();
    ExtExclusiveGateway exExclusiveGateway = JSON.parseObject(jsonStr, ExtExclusiveGateway.class);
    namespace = exExclusiveGateway.getNamespace();
    processKey = exExclusiveGateway.getProcessKey();

    ExtExclusiveGateway extExclusiveGateway = new ExtExclusiveGateway();
    extExclusiveGateway.setId(id);
    extExclusiveGateway.setName(name);
    extExclusiveGateway.setNamespace(namespace);
    extExclusiveGateway.setProcessKey(processKey);

    return extExclusiveGateway;
  }

  /**
   * 转换 activiti结束节点定义对象 为 内部扩展结束节点定义对象
   * 
   * @param endEvent activiti结束节点定义对象
   * @return 内部扩展结束节点定义对象
   */
  private ExtEndEvent convertFrom(EndEvent endEvent) {
    String id = endEvent.getId();
    String name = endEvent.getName();
    String namespace = null;
    String processKey = null;

    String jsonStr = endEvent.getDocumentation();
    ExtEndEvent exEndEvent = JSON.parseObject(jsonStr, ExtEndEvent.class);
    namespace = exEndEvent.getNamespace();
    processKey = exEndEvent.getProcessKey();

    ExtEndEvent extEndEvent = new ExtEndEvent();
    extEndEvent.setId(id);
    extEndEvent.setName(name);
    extEndEvent.setNamespace(namespace);
    extEndEvent.setProcessKey(processKey);

    return extEndEvent;
  }

  /**
   * 转换 内部扩展用户任务定义对象 为 activiti用户任务定义对象
   * 
   * @param extUserTask 内部扩展用户任务定义对象
   * @return activiti用户任务定义对象
   */
  private UserTask convertTo(ExtUserTask extUserTask) {
    String name = extUserTask.getName();
    String id = extUserTask.getId();
    String candidateGroups = extUserTask.getCandidateGroups();

    UserTask userTask = new UserTask();
    userTask.setName(name);
    userTask.setId(id);
    // 将ExtUserTask对象序列化并存入Documentation
    String userTaskStr = JSON.toJSONString(extUserTask);
    userTask.setDocumentation(userTaskStr);
    if (!StringUtils.isEmpty(candidateGroups)) {
      String[] cGroups = candidateGroups.split(",");
      List<String> canGroups = new ArrayList<String>();
      String candidateGroup = null;
      for (int i = 0; i < cGroups.length; ++i) {
        candidateGroup = cGroups[i];
        canGroups.add(candidateGroup);
      }
      userTask.setCandidateGroups(canGroups);
    }
    return userTask;
  }

  /**
   * 转换 内部扩展开始节点定义对象 为 activiti开始节点定义对象
   * 
   * @param extStartEvent
   * @return
   */
  private StartEvent convertTo(ExtStartEvent extStartEvent) {
    String id = extStartEvent.getId();
    String name = extStartEvent.getName();


    StartEvent startEvent = new StartEvent();
    startEvent.setId(id);
    startEvent.setName(name);

    String jsonStr = JSON.toJSONString(extStartEvent);
    startEvent.setDocumentation(jsonStr);

    return startEvent;
  }

  /**
   * 转换 内部扩展连线定义对象 为 activiti连线定义对象
   * 
   * @param extSequenceFlow 内部扩展连线定义对象
   * @return activiti连线定义对象
   */
  private SequenceFlow convertTo(ExtSequenceFlow extSequenceFlow) {
    String id = extSequenceFlow.getId();
    String name = extSequenceFlow.getName();
    String sourceRef = extSequenceFlow.getSourceRef();
    String targetRef = extSequenceFlow.getTargetRef();
    String conditionExpression = extSequenceFlow.getConditionExpression();


    SequenceFlow flow = new SequenceFlow();
    flow.setId(id);
    flow.setSourceRef(sourceRef);
    flow.setTargetRef(targetRef);
    flow.setName(name);
    if (StringUtils.isNotEmpty(conditionExpression)) {
      flow.setConditionExpression(conditionExpression);
    }

    String jsonStr = JSON.toJSONString(extSequenceFlow);
    flow.setDocumentation(jsonStr);

    return flow;
  }

  /**
   * 转换 内部扩展排他网关对象 为 activiti排他网关对象
   * 
   * @param extExclusiveGateway 内部扩展排他网关对象
   * @return activiti排他网关对象
   */
  private ExclusiveGateway convertTo(ExtExclusiveGateway extExclusiveGateway) {
    String id = extExclusiveGateway.getId();
    String name = extExclusiveGateway.getName();

    ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
    exclusiveGateway.setId(id);
    exclusiveGateway.setName(name);

    String jsonStr = JSON.toJSONString(extExclusiveGateway);
    exclusiveGateway.setDocumentation(jsonStr);

    return exclusiveGateway;
  }

  /**
   * 转换 内部扩展结束节点定义对象 为 activiti结束节点定义对象
   * 
   * @param extEndEvent 内部扩展结束节点定义对象
   * @return activiti结束节点定义对象
   */
  private EndEvent convertTo(ExtEndEvent extEndEvent) {
    String id = extEndEvent.getId();
    String name = extEndEvent.getName();

    EndEvent endEvent = new EndEvent();
    endEvent.setId(id);
    endEvent.setName(name);

    String jsonStr = JSON.toJSONString(extEndEvent);
    endEvent.setDocumentation(jsonStr);

    return endEvent;
  }

}
