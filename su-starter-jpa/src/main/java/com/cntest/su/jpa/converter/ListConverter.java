package com.cntest.su.jpa.converter;

import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.cntest.su.utils.StringUtils;

@Converter(autoApply = true)
public class ListConverter implements AttributeConverter<List<String>, String> {
  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    return StringUtils.listToString(attribute);
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    return StringUtils.stringToList(dbData);
  }
}

