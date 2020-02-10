package com.cntest.su.xss.jackson;

import java.io.IOException;

import com.cntest.su.xss.util.XssUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class XssModule extends SimpleModule {
  private static final long serialVersionUID = -1163964145819125663L;

  public XssModule() {
    super("jackson-datatype-xss");
    addDeserializer(String.class, new XssDeserializer());
  }

  class XssDeserializer extends StdScalarDeserializer<String> {
    private static final long serialVersionUID = -8377341848196463423L;

    public XssDeserializer() {
      super(String.class);
    }

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      String text = jp.getValueAsString();
      if (text != null) {
        text = XssUtils.clear(text);
      }
      return text;
    }
  }
}
