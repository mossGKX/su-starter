package com.cntest.su.ienum.jackson;

import com.cntest.su.ienum.IEnum;

public enum TestEnum implements IEnum {
  ONE("一", "1"), TWO("二", "2");

  private String text;
  private String value;

  private TestEnum(String text, String value) {
    this.text = text;
    this.value = value;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public String getValue() {
    return value;
  }
}
