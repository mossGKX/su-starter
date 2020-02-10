package com.cntest.su.jpa.audit.model;

import lombok.Data;

/**
 * 日志数据项。用于数据比较。
 */
@Data
public class LogData {
  private String text;
  private String origData;
  private String newData;

  public Boolean isChanged() {
    return newData != null && origData != null && !origData.equals(newData);
  }
}
