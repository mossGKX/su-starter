package com.cntest.su.job.manager.enums;

/**
 * 阻塞策略。
 */
public enum BlockStrategy {
  /** 单机串行 */
  SERIAL_EXECUTION,
  /** 丢弃后续调度 */
  DISCARD_LATER,
  /** 覆盖之前调度 */
  COVER_EARLY
}
