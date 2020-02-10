package com.cntest.su.process.model.definition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 内部扩展流程定义对象
 * 
 * @author caining
 *
 */
public class ExtProcess {

  /**
   * 流程元素定义列表
   */
  private List<ExtFlowElement> flowElementList = new ArrayList<ExtFlowElement>();

  /**
   * 流程元素定义Map
   */
  private Map<String, ExtFlowElement> flowElementMap = new LinkedHashMap<String, ExtFlowElement>();

  /**
   * 流程定义id
   */
  @Getter
  @Setter
  private String id;

  /**
   * 流程名称
   */
  @Getter
  @Setter
  private String name;

  /**
   * 流程命名空间
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
   * 获取 流程元素定义列表
   * 
   * @return 流程元素定义列表
   */
  public List<ExtFlowElement> getFlowElementList() {
    return flowElementList;
  }

  /**
   * 向流程定义对象中田间流程元素定义对象
   * 
   * @param element 流程元素定义对象
   */
  public void addFlowElement(ExtFlowElement element) {
    element.setNamespace(this.getNamespace());
    element.setProcessKey(this.getProcessKey());
    flowElementList.add(element);
    if (!StringUtils.isEmpty(element.getId())) {
      flowElementMap.put(element.getId(), element);
    }
  }

}
