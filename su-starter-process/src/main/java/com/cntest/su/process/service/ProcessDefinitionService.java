package com.cntest.su.process.service;

import java.io.IOException;

import com.cntest.su.process.model.definition.ExtProcess;

/**
 * 流程定义服务接口
 * 
 * @author caining
 *
 */
public interface ProcessDefinitionService {

  /**
   * 发布流程定义
   * 
   * @param exProcess 流程定义对象
   * @return 流程发布对象id
   */
  public String deployProcess(ExtProcess exProcess);

  /**
   * 保存流程定义流程图
   * 
   * @param deploymentId 流程发布id
   * @param processId 流程定义对象
   * @param processImgPath 流程图保存目录地址
   * @return 流程图绝对地址
   * @throws IOException
   */
  public String saveProcessImage(String deploymentId, String processId, String processImgPath)
      throws IOException;


}
