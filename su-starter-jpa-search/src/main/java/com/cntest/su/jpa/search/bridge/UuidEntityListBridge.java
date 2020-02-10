package com.cntest.su.jpa.search.bridge;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.bridge.StringBridge;

import com.cntest.su.jpa.entity.UuidEntity;
import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * UuidEntity列表字段属性全文索引桥接器。
 */
public class UuidEntityListBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    @SuppressWarnings("unchecked")
    List<UuidEntity> entities = (List<UuidEntity>) object;
    if (CollectionUtils.isEmpty(entities)) {
      return "";
    }
    List<String> enumValues = new ArrayList<>();
    for (UuidEntity entity : entities) {
      enumValues.add(entity.getId());
    }
    return StringUtils.join(enumValues, " ");
  }
}
