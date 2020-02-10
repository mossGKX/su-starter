package com.cntest.su.ienum.jackson;

import java.io.IOException;

import com.cntest.su.ienum.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * IEnum枚举转换组件。
 */
public class IEnumModule extends SimpleModule {
  private static final long serialVersionUID = 4364404372582339545L;

  /**
   * 构造方法。
   */
  public IEnumModule() {
    super("jackson-datatype-ienum");
    addSerializer(new IEnumSerializer());
  }

  /**
   * IEnum枚举序列化。
   */
  class IEnumSerializer extends JsonSerializer<IEnum> {
    @Override
    public Class<IEnum> handledType() {
      return IEnum.class;
    }

    @Override
    public void serialize(IEnum value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException {
      jgen.writeString(value.getValue());
    }
  }
}
