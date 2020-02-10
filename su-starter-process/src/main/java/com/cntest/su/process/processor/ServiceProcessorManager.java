package com.cntest.su.process.processor;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务处理器管理器
 * 
 * @author caining
 *
 */
public class ServiceProcessorManager {

  Map<String, ServiceProcessor> serviceProcessors = new HashMap<String, ServiceProcessor>();

  /**
   * 设置服务处理器
   * 
   * @param namespace
   * @param processKey
   * @param taskKey
   * @param serviceProcessor
   */
  public void putServiceProcessor(String namespace, String processKey, String taskKey,
      ServiceProcessor serviceProcessor) {
    String key = this.serviceProcessorKey(namespace, processKey, taskKey);
    this.serviceProcessors.put(key, serviceProcessor);
  }

  /**
   * 执行服务器处理器
   * 
   * @param namespace
   * @param processKey
   * @param taskKey
   * @param parameters
   * @return
   */
  public Object execute(String namespace, String processKey, String taskKey, Object parameters) {
    String key = this.serviceProcessorKey(namespace, processKey, taskKey);
    ServiceProcessor serviceProcessor = (ServiceProcessor) this.serviceProcessors.get(key);
    Object ret = serviceProcessor.execute(parameters);
    return ret;
  }


  private String serviceProcessorKey(String namespace, String processKey, String taskKey) {
    return namespace + ":" + taskKey;
  }

}
