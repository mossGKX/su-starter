package com.cntest.su.ienum.converter;

import org.springframework.core.convert.converter.Converter;

import com.cntest.su.ienum.IEnum;

/**
 * IEnum转换字符串组件。
 */
public class IEnumToString implements Converter<IEnum, String> {
  @Override
  public String convert(IEnum source) {
    return source.getValue();
  }
}
