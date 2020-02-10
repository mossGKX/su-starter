package com.cntest.su.web.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误响应对象。
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
  /** 错误编码 */
  private String code;
  /** 错误信息 */
  private String msg;
}
