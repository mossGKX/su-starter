package com.cntest.su.jpa.entity;

import org.springframework.core.convert.converter.Converter;

public class IdEntityToString implements Converter<IdEntity, String> {
  @Override
  public String convert(IdEntity source) {
    return source.getId().toString();
  }
}
