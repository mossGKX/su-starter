package com.cntest.su.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.time.LocalTime;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {
  @Override
  public String convertToDatabaseColumn(LocalTime attribute) {
    return attribute.toString("HH:mm:ss");
  }

  @Override
  public LocalTime convertToEntityAttribute(String dbData) {
    return new LocalTime(dbData);
  }
}
