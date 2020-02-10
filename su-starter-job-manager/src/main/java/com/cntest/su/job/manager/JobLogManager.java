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
 * 任务日志管理。
 */
public class JobLogManager {
  @Autowired
  private JobProperties jobProperties;
  @Autowired
  private ObjectMapper mapper;

  /**
   * 获取任务日志列表。
   * 
   * @param jobId 任务ID
   * @return 返回任务日志列表。
   */
  public List<JobLog> getLogs(String jobId) {
    String url = jobProperties.getManager().getUrl() + "/joblog/logs";
    Map<String, String> postParams = new HashMap<>();
    postParams.put("jobId", jobId);
    String response = HttpClientUtils.doPost(url, postParams);
    try {
      return mapper.readValue(response, new TypeReference<List<JobLog>>() {});
    } catch (Exception e) {
      throw new SysException("获取任务日志列表时发生异常。", e);
    }
  }

  /**
   * 获取日志详情。
   * 
   * @param logId 日志ID
   * @return 返回日志详情。
   */
  public JobLogDetail getDetail(String logId) {
    String url = jobProperties.getManager().getUrl() + "/joblog/detail";
    Map<String, String> postParams = new HashMap<>();
    postParams.put("logId", logId);
    String response = HttpClientUtils.doPost(url, postParams);
    try {
      ReturnT<JobLogDetail> result =
          mapper.readValue(response, new TypeReference<ReturnT<JobLogDetail>>() {});
      if (result.getCode() == HttpStatus.SC_OK) {
        return result.getContent();
      } else {
        throw new SysException("获取任务日志详情失败：" + result.toString());
      }
    } catch (Exception e) {
      throw new SysException("获取任务日志详情时发生异常。", e);
    }
  }
}
