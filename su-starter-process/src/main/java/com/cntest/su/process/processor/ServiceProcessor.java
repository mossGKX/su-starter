package com.cntest.su.process.processor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.process.annotation.ProcessServiceProcessor;

/**
 * 业务服务处理器
 * 
 * @author caining
 *
 */
public abstract class ServiceProcessor implements InitializingBean {

  @Autowired
  private ServiceProcessorManager serviceProcessorManager;

  /**
   * 业务处理执行方法
   * 
   * @param parameters
   * @return
   */
  public abstract Object execute(Object parameters);


  @Override
  public void afterPropertiesSet() throws Exception {
    ProcessServiceProcessor processServiceProcessor =
        this.getClass().getAnnotation(ProcessServiceProcessor.class);
    String namespace = processServiceProcessor.namespace();
    String processKey = processServiceProcessor.processKey();
    String taskKey = processServiceProcessor.taskKey();
    this.serviceProcessorManager.putServiceProcessor(namespace, processKey, taskKey, this);
  }

}
