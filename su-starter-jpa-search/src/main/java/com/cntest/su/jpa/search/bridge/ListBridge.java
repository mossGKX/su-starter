package com.cntest.su.jpa.search.bridge;

import java.util.List;

import org.hibernate.search.bridge.StringBridge;

import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 字符串列表字段全文索引桥接器。
 */
public class ListBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    @SuppressWarnings("unchecked")
    List<String> strs = (List<String>) object;
    if (CollectionUtils.isEmpty(strs)) {
      return "";
    }
    return StringUtils.join(strs, " ");
  }
}
