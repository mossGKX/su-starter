package com.cntest.su.jpa.search.bridge;

import org.hibernate.search.bridge.StringBridge;

import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 字符串数组字段全文索引桥接器。
 */
public class ArrayBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    String[] strs = (String[]) object;
    if (CollectionUtils.isEmpty(strs)) {
      return "";
    }
    return StringUtils.join(strs, " ");
  }
}
