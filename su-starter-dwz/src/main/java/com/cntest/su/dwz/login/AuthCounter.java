package com.cntest.su.dwz.login;

import java.io.Serializable;

/**
 * 验证次数计数器。
 */
public class AuthCounter implements Serializable {
  private static final long serialVersionUID = 3483821539019952011L;
  private Integer allowRetries = 3;
  private Integer retries = 0;

  /**
   * 增加验证次数计数。
   */
  public void add() {
    retries++;
  }

  /**
   * 判断是否已经超过允许的重试次数。
   * 
   * @return 如果已超过允许的重试次数返回true，否则返回false。
   */
  public Boolean isOver() {
    return retries >= allowRetries;
  }

  /**
   * 验证次数清零。
   */
  public void clean() {
    retries = 0;
  }
}
