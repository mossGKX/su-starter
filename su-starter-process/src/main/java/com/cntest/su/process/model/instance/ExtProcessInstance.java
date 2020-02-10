package com.cntest.su.process.model.instance;

import com.cntest.su.process.model.definition.ExtProcess;

/**
 * 内部扩展流程实例对象
 * 
 * @author caining
 *
 */
public class ExtProcessInstance {

  /**
   * 流程实例id
   */
  private String id;

  /**
   * 流程定义对象
   */
  private ExtProcess extProcess;

  /**
   * 获取 流程实例id
   * 
   * @return 流程实例id
   */
  public String getId() {
    return id;
  }

  /**
   * 设置 流程实例id
   * 
   * @param id 流程实例id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 获取 流程定义对象
   * 
   * @return 流程定义对象
   */
  public ExtProcess getExtProcess() {
    return extProcess;
  }

  /**
   * 设置 流程定义对象
   * 
   * @param extProcess 流程定义对象
   */
  public void setExtProcess(ExtProcess extProcess) {
    this.extProcess = extProcess;
  }

}
