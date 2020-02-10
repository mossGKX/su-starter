package com.cntest.su.ienum.bridge;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.bridge.StringBridge;

import com.cntest.su.ienum.IEnum;
import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * IEnumList枚举类型字段对value属性进行全文索引的桥接器。
 */
public class IEnumListValueBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    @SuppressWarnings("unchecked")
    List<IEnum> ienums = (List<IEnum>) object;
    if (CollectionUtils.isEmpty(ienums)) {
      return "";
    }
    List<String> enumValues = new ArrayList<>();
    for (IEnum ienum : ienums) {
      enumValues.add(ienum.getValue());
    }
    return StringUtils.join(enumValues, " ");
  }
}
