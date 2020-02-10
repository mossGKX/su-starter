package com.cntest.su.jpa.entity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.cntest.su.jpa.dao.DaoUtils;

public class StringToIdEntity implements ConverterFactory<String, IdEntity> {
  @Override
  public <T extends IdEntity> Converter<String, T> getConverter(Class<T> targetType) {
    return new StringToIdEntityConverter<>(targetType);
  }

  /**
   * 字符串转换成IdEntity转换器。
   * 
   * @param <T> 转换类型
   */
  private class StringToIdEntityConverter<T extends IdEntity> implements Converter<String, T> {
    private final Class<T> toClass;

    /**
     * 构造方法。
     * 
     * @param toClass 转换目标类
     */
    public StringToIdEntityConverter(Class<T> toClass) {
      this.toClass = toClass;
    }

    /**
     * 将字符串值转换为IdEntity对象。
     * 
     * @param id ID
     * @return 返回IdEntity对象。
     */
    public T convert(String id) {
      if (id == null) {
        return null;
      }
      return DaoUtils.getEntity(toClass, Long.parseLong(id));
    }
  }
}
