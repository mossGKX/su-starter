package com.cntest.su.exception;

/**
 * 业务异常。
 */
public class BizException extends RuntimeException {
  private static final long serialVersionUID = 5342133798884871742L;
  private final String code;
  private final String msg;

  /**
   * 构造方法。
   * 
   * @param code 编码
   * @param msg 异常信息
   */
  public BizException(String code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
