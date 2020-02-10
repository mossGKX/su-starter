package com.cntest.su.process.event.execution;

import java.util.Map;

import com.cntest.su.process.model.definition.ExtFlowElement;

import lombok.Getter;
import lombok.Setter;

/**
 * Execution事件代理对象
 * 
 * @author caining
 *
 */
public class EventDelegateExecution {

  /**
   * 事件
   */
  @Getter
  @Setter
  private String event;

  /**
   * 流程实例id
   */
  @Getter
  @Setter
  private String processInstanceId;

  /**
   * 流程变量
   */
  @Getter
  @Setter
  private Map<String, Object> variables;

  /**
   * 流程元素
   */
  @Getter
  @Setter
  private ExtFlowElement extFlowElement;

}
