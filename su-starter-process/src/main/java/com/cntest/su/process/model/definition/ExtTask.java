package com.cntest.su.process.model.definition;

import java.util.Map;

/**
 * 内部扩展抽象任务定义对象
 * 
 * @author caining
 *
 */
public class ExtTask extends ExtFlowElement {

  /**
   * 所属用户组列表（admin,student）
   */
  private String candidateGroups;

  /**
   * 功能名称
   */
  private String functionName;

  /**
   * 任务表单（渠道：表单相对url）
   */
  private Map<String, String> forms;

  /**
   * 获取 所属用户组列表（admin,student）
   * 
   * @return 所属用户组列表（admin,student）
   */
  public String getCandidateGroups() {
    return candidateGroups;
  }

  /**
   * 设置 所属用户组列表（admin,student）
   * 
   * @param candidateGroups 所属用户组列表（admin,student）
   */
  public void setCandidateGroups(String candidateGroups) {
    this.candidateGroups = candidateGroups;
  }

  /**
   * 获取 功能名称
   * 
   * @return 功能名称
   */
  public String getFunctionName() {
    return functionName;
  }

  /**
   * 设置 功能名称
   * 
   * @param functionName 功能名称
   */
  public void setFunctionName(String functionName) {
    this.functionName = functionName;
  }

  /**
   * 获取 任务表单（渠道：表单相对url）
   * 
   * @return 任务表单（渠道：表单相对url）
   */
  public Map<String, String> getForms() {
    return forms;
  }

  /**
   * 设置 任务表单（渠道：表单相对url）
   * 
   * @param forms 任务表单（渠道：表单相对url）
   */
  public void setForms(Map<String, String> forms) {
    this.forms = forms;
  }

}
