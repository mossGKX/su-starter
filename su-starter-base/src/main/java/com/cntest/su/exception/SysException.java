package com.cntest.su.exception;

/**
 * 系统异常。
 */
public class SysException extends RuntimeException {
  private static final long serialVersionUID = -8958718055404910646L;

  /**
   * 构造方法。
   * 
   * @param message 异常信息
   */
  public SysException(String message) {
    super(message);
  }

  /**
   * 构造方法。
   * 
   * @param cause 异常原因
   */
  public SysException(Throwable cause) {
    super(cause);
  }

  /**
   * 构造方法。
   * 
   * @param message 异常信息
   * @param cause 异常原因
   */
  public SysException(String message, Throwable cause) {
    super(message, cause);
  }
}
