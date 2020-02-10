package com.cntest.su.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cntest.su.constant.Encoding;
import com.cntest.su.exception.SysException;

/**
 * URL工具类。
 */
public class UrlUtils {
  /**
   * 对URL进行编码。
   * 
   * @param url URL地址
   * @return 返回编码后的URL。
   */
  public static String encode(String url) {
    return encode(url, Encoding.UTF8);
  }

  /**
   * 对URL进行编码。
   * 
   * @param url URL地址
   * @param enc 编码格式
   * @return 返回编码后的URL。
   */
  public static String encode(String url, String enc) {
    try {
      return URLEncoder.encode(url, enc);
    } catch (UnsupportedEncodingException e) {
      throw new SysException("对URL进行编码时发生异常。", e);
    }
  }

  /**
   * 对URL进行解码。
   * 
   * @param url URL地址
   * @return 返回解码的URL。
   */
  public static String decode(String url) {
    return decode(url, Encoding.UTF8);
  }

  /**
   * 对URL进行解码。
   * 
   * @param url URL地址
   * @param enc 编码格式
   * @return 返回解码的URL。
   */
  public static String decode(String url, String enc) {
    try {
      return URLDecoder.decode(url, enc);
    } catch (UnsupportedEncodingException e) {
      throw new SysException("对URL进行解码时发生异常。", e);
    }
  }

  /**
   * 生成请求参数字符串。
   * 
   * @param params 请求参数
   * @return 返回请求参数字符串。
   */
  public static String genParamsStr(Map<String, String> params) {
    String paramsStr = "";
    if (CollectionUtils.isNotEmpty(params)) {
      List<String> datas = new ArrayList<>();
      for (Entry<String, String> param : params.entrySet()) {
        datas.add(param.getKey() + "=" + param.getValue());
      }
      paramsStr = StringUtils.join(datas, "&");
    }
    return paramsStr;
  }

  /**
   * 生成请求参数字符串。
   * 
   * @param params 请求参数
   * @param enc 编码格式
   * @return 返回请求参数字符串。
   */
  public static String genParamsStr(Map<String, String> params, String enc) {
    Map<String, String> encodeParams = new LinkedHashMap<>();
    for (Entry<String, String> param : params.entrySet()) {
      encodeParams.put(param.getKey(), encode(param.getValue(), enc));
    }
    return genParamsStr(encodeParams);
  }

  /**
   * 私有构造方法。
   */
  private UrlUtils() {}
}
