package com.cntest.su.process.model.definition;

import lombok.Getter;
import lombok.Setter;

/**
 * 流程元素定义对象
 * 
 * @author caining
 *
 */
public class ExtFlowElement {

  /**
   * 流程元素定义对象id
   */
  private String id;

  /**
   * 流程元素定义对象名称
   */
  private String name;

  /**
   * 命名空间
   */
  @Getter
  @Setter
  private String namespace;

  /**
   * 基础流程定义key（对某种业务流程的基础配置）
   */
  @Getter
  @Setter
  private String processKey;

  /**
   * 获取 流程元素定义对象id
   * 
   * @return 流程元素定义对象id
   */
  public String getId() {
    return id;
  }

  /**
   * 设置 流程元素定义对象id
   * 
   * @param id 流程元素定义对象id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 获取 流程元素定义对象名称
   * 
   * @return 流程元素定义对象名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置 流程元素定义对象名称
   * 
   * @param name 流程元素定义对象名称
   */
  public void setName(String name) {
    this.name = name;
  }

}
