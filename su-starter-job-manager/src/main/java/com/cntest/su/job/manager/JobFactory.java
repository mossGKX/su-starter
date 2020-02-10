package com.cntest.su.job.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.job.config.JobProperties;
import com.cntest.su.job.manager.enums.RouteStrategy;

/**
 * 任务工厂。
 */
public class JobFactory {
  @Autowired
  private JobProperties jobProperties;

  /**
   * 创建一个常规任务。
   * 
   * @param handler 处理器名称
   * @param desc 任务描述
   * @return 返回创建的任务。
   */
  public Job createJob(String handler, String desc) {
    Job job = new Job();
    job.setHandler(handler);
    job.setDesc(desc);
    job.setWorkerGroupId(jobProperties.getManager().getWorkerGroupId());
    return job;
  }

  /**
   * 创建一个分片任务。
   * 
   * @param handler 处理器名称
   * @param desc 任务描述
   * @return 返回创建的任务。
   */
  public Job createShardingJob(String handler, String desc) {
    Job job = createJob(handler, desc);
    job.setRouteStrategy(RouteStrategy.SHARDING_BROADCAST);
    return job;
  }
}
