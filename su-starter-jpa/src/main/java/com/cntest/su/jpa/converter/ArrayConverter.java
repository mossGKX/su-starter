package com.cntest.su.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.cntest.su.utils.StringUtils;

@Converter(autoApply = true)
public class ArrayConverter implements AttributeConverter<String[], String> {
  @Override
  public String convertToDatabaseColumn(String[] attribute) {
    return StringUtils.arrayToString(attribute);
  }

  @Override
  public String[] convertToEntityAttribute(String dbData) {
    return StringUtils.stringToArray(dbData);
  }
}
