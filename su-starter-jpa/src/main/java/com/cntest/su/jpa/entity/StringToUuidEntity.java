package com.cntest.su.jpa.entity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.cntest.su.jpa.dao.DaoUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 字符串转换成UuidEntity转换器工厂。
 */
public class StringToUuidEntity implements ConverterFactory<String, UuidEntity> {
  @Override
  public <T extends UuidEntity> Converter<String, T> getConverter(Class<T> targetType) {
    return new StringToUuidEntityConverter<>(targetType);
  }

  /**
   * 字符串转换成UuidEntity转换器。
   * 
   * @param <T> 转换类型
   */
  private class StringToUuidEntityConverter<T extends UuidEntity> implements Converter<String, T> {
    private final Class<T> toClass;

    /**
     * 构造方法。
     * 
     * @param toClass 转换目标类
     */
    public StringToUuidEntityConverter(Class<T> toClass) {
      this.toClass = toClass;
    }

    /**
     * 将字符串值转换为UuidEntity对象。
     * 
     * @param id ID
     * @return 返回UuidEntity对象。
     */
    public T convert(String id) {
      if (StringUtils.isBlank(id)) {
        return null;
      }
      return DaoUtils.getEntity(toClass, id.trim());
    }
  }
}
