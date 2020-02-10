package com.cntest.su.process.activiti.converter;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;

import com.alibaba.fastjson.JSON;
import com.cntest.su.process.model.definition.ExtUserTask;
import com.cntest.su.process.model.instance.ExtTaskInstance;

public class ActivitiInstanceConverter {

  public ExtTaskInstance convertTaskInstance(DelegateTask delegateTask) {
    String taskId = delegateTask.getId();
    String taskKey = delegateTask.getTaskDefinitionKey();
    String name = delegateTask.getName();
    String candidateGroups = null;
    String functionName = null;
    Map<String, String> forms = null;
    String namespace = null;
    String processKey = null;
    String processInstanceId = delegateTask.getProcessInstanceId();


    /**
     * 将Documentation存储的json转换为ExtUserTask，获取相关信息
     */
    String userTaskJsonStr = delegateTask.getDescription();
    ExtUserTask exUserTask = JSON.parseObject(userTaskJsonStr, ExtUserTask.class);
    functionName = exUserTask.getFunctionName();
    forms = exUserTask.getForms();
    candidateGroups = exUserTask.getCandidateGroups();
    namespace = exUserTask.getNamespace();
    processKey = exUserTask.getProcessKey();

    /**
     * 构建 内部扩展用户任务定义对象
     */
    ExtUserTask extUserTask = new ExtUserTask();
    extUserTask.setId(taskKey);
    extUserTask.setName(name);
    extUserTask.setCandidateGroups(candidateGroups);
    extUserTask.setFunctionName(functionName);
    extUserTask.setForms(forms);
    extUserTask.setNamespace(namespace);
    extUserTask.setProcessKey(processKey);

    //
    ExtTaskInstance extTaskInstance = new ExtTaskInstance();
    extTaskInstance.setId(taskId);
    extTaskInstance.setProcessInstanceId(processInstanceId);
    extTaskInstance.setExtTask(extUserTask);

    return extTaskInstance;
  }

}
