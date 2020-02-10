package com.cntest.su.process.model.definition;

/**
 * 内部扩展连线定义对象
 * 
 * @author caining
 *
 */
public class ExtSequenceFlow extends ExtFlowElement {

  /**
   * 源定义对象id
   */
  private String sourceRef;

  /**
   * 目标定义对象id
   */
  private String targetRef;

  /**
   * 连线条件表达式
   */
  private String conditionExpression;

  /**
   * 获取 源定义对象id
   * 
   * @return 源定义对象id
   */
  public String getSourceRef() {
    return sourceRef;
  }

  /**
   * 设置 源定义对象id
   * 
   * @param sourceRef 源定义对象id
   */
  public void setSourceRef(String sourceRef) {
    this.sourceRef = sourceRef;
  }

  /**
   * 获取 目标定义对象id
   * 
   * @return 目标定义对象id
   */
  public String getTargetRef() {
    return targetRef;
  }

  /**
   * 设置 目标定义对象id
   * 
   * @param targetRef 目标定义对象id
   */
  public void setTargetRef(String targetRef) {
    this.targetRef = targetRef;
  }

  /**
   * 获取 连线条件表达式
   * 
   * @return 连线条件表达式
   */
  public String getConditionExpression() {
    return conditionExpression;
  }

  /**
   * 设置 连线条件表达式
   * 
   * @param conditionExpression 连线条件表达式
   */
  public void setConditionExpression(String conditionExpression) {
    this.conditionExpression = conditionExpression;
  }


}
