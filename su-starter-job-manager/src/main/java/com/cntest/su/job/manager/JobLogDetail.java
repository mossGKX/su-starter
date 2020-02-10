package com.cntest.su.job.manager;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 任务日志详情。
 */
@Data
public class JobLogDetail {
  /** 起始行 */
  @JsonProperty("fromLineNum")
  private Integer startLine;
  /** 截止行 */
  @JsonProperty("toLineNum")
  private Integer endLine;
  /** 日志内容 */
  @JsonProperty("logContent")
  private String content;
}
