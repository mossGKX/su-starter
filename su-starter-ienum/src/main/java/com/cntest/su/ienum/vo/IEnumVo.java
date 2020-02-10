package com.cntest.su.ienum.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cntest.su.ienum.IEnum;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class IEnumVo {
  private String name;
  private String des;
  private Map<String, String> items = new LinkedHashMap<>();

  public IEnumVo(Class<? extends IEnum> ienumClass) {
    if (ienumClass.isAnnotationPresent(ApiModel.class)) {
      ApiModel anno = ienumClass.getAnnotation(ApiModel.class);
      name = anno.value();
      des = anno.description();
    } else {
      name = ienumClass.getSimpleName();
      des = ienumClass.getSimpleName();
    }
    for (IEnum ienum : ienumClass.getEnumConstants()) {
      items.put(ienum.getValue(), ienum.getText());
    }
  }
}
