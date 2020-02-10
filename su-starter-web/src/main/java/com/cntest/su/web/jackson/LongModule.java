package com.cntest.su.web.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Long类型转换组件。因为前端js对Long类型处理的精度丢失问题，将后台的Long类型转换成String处理。
 */
public class LongModule extends SimpleModule {
  private static final long serialVersionUID = 5784648344215362269L;

  /**
   * 构造方法。
   */
  public LongModule() {
    super("jackson-datatype-long");
    addSerializer(Long.class, ToStringSerializer.instance);
    addSerializer(Long.TYPE, ToStringSerializer.instance);
  }
}
