package com.cntest.su.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cntest.su.exception.SysException;

/**
 * 字符串工具类。
 */
public class StringUtils {
  /**
   * 判断指定字符串是否为空。
   * 
   * @param str 待判断的字符串
   * @return 返回指定字符串是否为空。
   */
  public static Boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  /**
   * 判断指定字符串是否不为空。
   * 
   * @param str 待判断的字符串
   * @return 返回指定字符串是否不为空。
   */
  public static Boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  /**
   * 判断指定字符串是否为空字符串。
   * 
   * @param str 待判断的字符串
   * @return 返回指定字符串是否为空字符串。
   */
  public static Boolean isBlank(String str) {
    if (isNotEmpty(str)) {
      return str.codePoints().filter(c -> !Character.isWhitespace(c)).count() == 0;
    } else {
      return true;
    }
  }

  /**
   * 判断指定字符串是否不为空字符串。
   * 
   * @param str 待判断的字符串
   * @return 返回指定字符串是否不为空字符串。
   */
  public static Boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  /**
   * 截取指定分隔符前的字符串内容。
   * 
   * @param str 待截取的字符串
   * @param separator 分隔符
   * @return 返回指定分隔符前的字符串内容。
   */
  public static String substringBefore(String str, String separator) {
    int pos = str.indexOf(separator);
    if (pos == -1) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * 截取最后一个分隔符前的字符串内容。
   * 
   * @param str 待截取的字符串
   * @param separator 分隔符
   * @return 返回最后一个分隔符前的字符串内容。
   */
  public static String substringBeforeLast(String str, String separator) {
    int pos = str.lastIndexOf(separator);
    if (pos == -1) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * 截取指定分隔符后的字符串内容。
   * 
   * @param str 待截取的字符串
   * @param separator 分隔符
   * @return 返回指定分隔符后的字符串内容。
   */
  public static String substringAfter(String str, String separator) {
    int pos = str.indexOf(separator);
    if (pos == -1) {
      return "";
    }
    return str.substring(pos + separator.length());
  }

  /**
   * 截取最后一个分隔符后的字符串内容。
   * 
   * @param str 待截取的字符串
   * @param separator 分隔符
   * @return 返回最后一个分隔符后的字符串内容。
   */
  public static String substringAfterLast(String str, String separator) {
    int pos = str.lastIndexOf(separator);
    if (pos == -1 || pos == (str.length() - separator.length())) {
      return "";
    }
    return str.substring(pos + separator.length());
  }

  /**
   * 截取两个分隔符之间的字符串。
   * 
   * @param str 待截取的字符串
   * @param startSeparator 开始分隔符
   * @param endSeparator 结束分隔符
   * @return 返回两个分隔符之间的字符串。
   */
  public static String substringBetween(String str, String startSeparator, String endSeparator) {
    if (str == null || startSeparator == null || endSeparator == null) {
      return null;
    }
    String rs = substringAfter(str, startSeparator);
    rs = substringBefore(rs, endSeparator);
    return rs;
  }

  /**
   * 截取指定起始位置和截取长度的字符串。
   * 
   * @param str 待截取的字符串
   * @param pos 起始位置
   * @param len 截取长度
   * @return 返回指定起始位置和截取长度的字符串。
   */
  public static String mid(String str, int pos, int len) {
    if (str.length() <= (pos + len)) {
      return str.substring(pos);
    }
    return str.substring(pos, pos + len);
  }

  /**
   * 将一个字符串数组用指定分隔符串联起来。
   * 
   * @param strs 字符串数组
   * @param separator 分隔符
   * @return 返回串联起来的字符串。
   */
  public static String join(String[] strs, String separator) {
    return Stream.of(strs).collect(Collectors.joining(separator));
  }

  /**
   * 将一个字符串列表用指定分隔符串联起来。
   * 
   * @param strs 字符串数组
   * @param separator 分隔符
   * @return 返回串联起来的字符串数组。
   */
  public static String join(Collection<String> strs, String separator) {
    return join(strs.toArray(new String[] {}), separator);
  }

  /**
   * 对字符串进行字符集转换。
   * 
   * @param str 字符串
   * @param origEncoding 原字符集编码
   * @param destEncoding 新字符集编码
   * @return 返回转换后的字符串。
   */
  public static String encode(String str, String origEncoding, String destEncoding) {
    try {
      return new String(str.getBytes(origEncoding), destEncoding);
    } catch (UnsupportedEncodingException e) {
      throw new SysException("对字符串进行字符集转换时发生异常", e);
    }
  }

  /**
   * 将固定格式的字符串转换成一个字符数组。
   * 
   * @param strs 字符串
   * @return 返回一个字符数组。
   */
  public static String[] stringToArray(String strs) {
    String[] result = new String[0];
    if (isNotBlank(strs)) {
      result = strs.split(",");
    }
    return result;
  }

  /**
   * 将字符数组转换成一个固定格式的字符串。
   * 
   * @param array 字符数组
   * @return 返回固定格式的字符串。
   */
  public static String arrayToString(String[] array) {
    return join(array, ",");
  }

  /**
   * 将固定格式的字符串转换成一个字符列表。
   * 
   * @param strs 字符串
   * @return 返回一个字符列表。
   */
  public static List<String> stringToList(String strs) {
    return CollectionUtils.toList(stringToArray(strs));
  }

  /**
   * 将字符列表转换成一个固定格式的字符串。
   * 
   * @param list 字符列表
   * @return 返回固定格式的字符串。
   */
  public static String listToString(List<String> list) {
    return join(list, ",");
  }

  /**
   * 将固定格式的字符串转换成一个Map。
   * 
   * @param strs 字符串
   * @return 返回一个Map。
   */
  public static Map<String, String> stringToMap(String strs) {
    Map<String, String> result = new LinkedHashMap<>();
    if (StringUtils.isNotBlank(strs)) {
      for (String value : strs.split(",")) {
        String[] tmpStrs = value.split(":");
        result.put(tmpStrs[0], tmpStrs[1]);
      }
    }
    return result;
  }

  /**
   * 将一个Map转换成固定格式的字符串。
   * 
   * @param map Map
   * @return 返回固定格式的字符串。
   */
  public static String mapToString(Map<String, String> map) {
    List<String> result = new ArrayList<>();
    for (Entry<String, String> entry : map.entrySet()) {
      result.add(entry.getKey() + ":" + entry.getValue());
    }
    return StringUtils.join(result, ",");
  }

  /**
   * 私有构造方法。
   */
  private StringUtils() {}
}
