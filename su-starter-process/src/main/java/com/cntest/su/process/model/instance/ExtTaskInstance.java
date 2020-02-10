package com.cntest.su.process.model.instance;

import com.cntest.su.process.model.definition.ExtTask;

import lombok.Getter;
import lombok.Setter;

/**
 * 内部扩展任务实例对象
 * 
 * @author caining
 *
 */
public class ExtTaskInstance {

  /**
   * 任务实例id
   */
  private String id;

  /**
   * 流程实例id
   */
  @Getter
  @Setter
  private String processInstanceId;

  /**
   * 内部扩展抽象任务定义对象
   */
  private ExtTask extTask;

  /**
   * 获取 任务实例id
   * 
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * 设置 任务实例id
   * 
   * @param id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 获取 内部扩展抽象任务定义对象
   * 
   * @return
   */
  public ExtTask getExtTask() {
    return extTask;
  }

  /**
   * 设置 内部扩展抽象任务定义对象
   * 
   * @param extTask
   */
  public void setExtTask(ExtTask extTask) {
    this.extTask = extTask;
  }

}
