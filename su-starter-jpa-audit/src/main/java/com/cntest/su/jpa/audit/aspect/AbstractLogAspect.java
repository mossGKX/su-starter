package com.cntest.su.jpa.audit.aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.jpa.audit.entity.BizLog;
import com.cntest.su.jpa.audit.service.BizLogService;
import com.cntest.su.message.MessageSource;
import com.cntest.su.utils.AspectUtils;
import com.cntest.su.utils.CollectionUtils;

/**
 * 日志切面基类。
 */
public abstract class AbstractLogAspect {
  @Autowired
  private BizLogService bizLogService;
  @Autowired
  private MessageSource messageSource;

  /**
   * 保存业务日志。
   * 
   * @param bizLog 业务日志
   */
  protected void saveBizLog(BizLog bizLog) {
    bizLogService.log(bizLog);
  }

  /**
   * 获取日志信息。
   * 
   * @param code 日志信息编码
   * @param varFieldNames 变量字段名称
   * @param params 方法参数
   * @return 返回日志信息。
   */
  protected String getMessage(String code, String[] varFieldNames, Map<String, Object> params) {
    List<Object> vars = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(varFieldNames)) {
      for (String fieldName : varFieldNames) {
        vars.add(AspectUtils.getParam(fieldName, params));
      }
    }
    return messageSource.get(code, vars.toArray());
  }
}
