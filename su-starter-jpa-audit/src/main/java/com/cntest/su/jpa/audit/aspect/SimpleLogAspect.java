package com.cntest.su.jpa.audit.aspect;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.cntest.su.jpa.audit.annotation.SimpleLog;
import com.cntest.su.jpa.audit.entity.BizLog;
import com.cntest.su.utils.AspectUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 简单业务日志切面。
 */
@Aspect
public class SimpleLogAspect extends AbstractLogAspect {
  /**
   * 切面处理方法。
   * 
   * @param joinPoint 切入点
   * @param simpleLog 日志注解
   * @throws Throwable 切面处理失败时抛出异常。
   * @return 返回方法本身返回的对象。
   */
  @Around("@annotation(simpleLog)")
  public Object around(ProceedingJoinPoint joinPoint, SimpleLog simpleLog) throws Throwable {
    Map<String, Object> params = AspectUtils.getMethodParams(joinPoint);
    BizLog bizLog = new BizLog();
    bizLog.setEntityId(getEntityId(simpleLog.entityId(), params));
    bizLog.setMessage(getMessage(simpleLog.code(), simpleLog.vars(), params));
    Object result = joinPoint.proceed();
    saveBizLog(bizLog);
    return result;
  }

  /**
   * 获取业务实体ID。
   * 
   * @param entityId 业务实体ID名称
   * @param params 方法参数
   * @return 返回业务实体ID。
   */
  private String getEntityId(String entityId, Map<String, Object> params) {
    if (StringUtils.isBlank(entityId)) {
      return null;
    }
    Object entityField = AspectUtils.getParam(entityId, params);
    if (entityField != null) {
      return entityField.toString();
    }
    return null;
  }
}
