package com.cntest.su.job.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.exception.SysException;
import com.cntest.su.job.config.JobProperties;
import com.cntest.su.utils.HttpClientUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxl.job.core.biz.model.ReturnT;

/**
 * 任务管理组件。
 */
public class JobManager {
  @Autowired
  private JobProperties jobProperties;
  @Autowired
  private ObjectMapper mapper;

  /**
   * 新增任务。
   * 
   * @param job 任务
   * @return 返回新增任务ID。
   */
  public String add(Job job) {
    ReturnT<String> result = doPost("add", job.genPostParams());
    return result.getContent();
  }

  /**
   * 触发任务。
   * 
   * @param jobId 任务ID
   * @param params 任务参数
   */
  public void trigger(String jobId, String params) {
    Map<String, String> postParams = genJobParams(jobId);
    postParams.put("executorParam", params);
    doPost("trigger", postParams);
  }

  /**
   * 启动任务。
   * 
   * @param jobId 任务ID
   */
  public void start(String jobId) {
    doPost("start", genJobParams(jobId));
  }

  /**
   * 停止任务。
   * 
   * @param jobId 任务ID
   */
  public void stop(String jobId) {
    doPost("stop", genJobParams(jobId));
  }

  /**
   * 移除任务。
   * 
   * @param jobId 任务ID
   */
  public void remove(String jobId) {
    doPost("remove", genJobParams(jobId));
  }

  /**
   * 判断指定的任务是否正在运行中。
   * 
   * @param jobId 任务ID
   * @return 如果正在运行中返回true，否则返回false。
   */
  public Boolean isRunning(String jobId) {
    String url = jobProperties.getManager().getUrl() + "/joblog/pageList";
    Map<String, String> postParams = new HashMap<>();
    postParams.put("jobGroup", jobProperties.getManager().getWorkerGroupId().toString());
    postParams.put("logStatus", "3");
    postParams.put("jobId", jobId);
    String response = HttpClientUtils.doPost(url, postParams);
    try {
      Map<?, ?> map = mapper.readValue(response, Map.class);
      return !((List<?>) map.get("data")).isEmpty();
    } catch (Exception e) {
      throw new SysException("判断是否存在运行中任务时发生异常。", e);
    }
  }

  /**
   * 生成任务ID参数Map。
   * 
   * @param jobId 任务ID
   * @return 返回参数Map。
   */
  private Map<String, String> genJobParams(String jobId) {
    Map<String, String> postParams = new HashMap<>();
    postParams.put("id", jobId);
    return postParams;
  }

  /**
   * 远程调用接口。
   * 
   * @param action 接口名称
   * @param postParams 参数
   * @return 返回响应结果。
   */
  private ReturnT<String> doPost(String action, Map<String, String> postParams) {
    String url = jobProperties.getManager().getUrl() + "/jobinfo/" + action;
    String response = HttpClientUtils.doPost(url, postParams);
    try {
      ReturnT<String> result = mapper.readValue(response, new TypeReference<ReturnT<String>>() {});
      if (result.getCode() == HttpStatus.SC_OK) {
        return result;
      } else {
        throw new SysException("调用任务调度服务失败：" + result.toString());
      }
    } catch (Exception e) {
      throw new SysException("调用任务调度服务时发生异常。", e);
    }
  }
}
