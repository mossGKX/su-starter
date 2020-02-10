package com.cntest.su.job.manager;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 任务日志。
 */
@Data
public class JobLog {
  /** ID */
  private Integer id;
  /** 执行器IP地址 */
  @JsonProperty("executorAddress")
  private String workerIp;
  /** 处理器 */
  @JsonProperty("executorHandler")
  private String handler;
  /** 任务参数 */
  @JsonProperty("executorParam")
  private String params;
  /** 分片参数 */
  @JsonProperty("executorShardingParam")
  private String shardingParams;
  /** 重试次数 */
  @JsonProperty("executorFailRetryCount")
  private Integer retry;
  /** 调度时间 */
  private Date triggerTime;
  /** 调度结果 */
  private Integer triggerCode;
  /** 调度返回信息 */
  private String triggerMsg;
  /** 执行时间 */
  private Date handleTime;
  /** 执行结果 */
  private Integer handleCode;
  /** 执行返回信息 */
  private String handleMsg;
}
