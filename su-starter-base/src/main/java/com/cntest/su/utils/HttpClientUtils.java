package com.cntest.su.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cntest.su.constant.Encoding;
import com.cntest.su.exception.SysException;

import lombok.extern.slf4j.Slf4j;

/**
 * HttpClient工具类。
 */
@Slf4j
public class HttpClientUtils {
  /**
   * 调用HttpGet请求。
   * 
   * @param url URL地址
   * @return 返回响应消息字符串。
   */
  public static String doGet(String url) {
    return doGet(url, null);
  }

  /**
   * 调用HttpGet请求。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @return 返回响应消息字符串。
   */
  public static String doGet(String url, Map<String, String> params) {
    return doGet(url, params, Encoding.UTF8);
  }

  /**
   * 调用HttpGet请求。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @param encoding 编码格式
   * @return 返回响应消息字符串。
   */
  public static String doGet(String url, Map<String, String> params, String encoding) {
    return doGet(url, params, encoding, encoding);
  }

  /**
   * 调用HttpGet请求。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @param submitEncoding 提交参数编码格式
   * @param resultEncoding 返回结果编码格式
   * @return 返回响应消息字符串。
   */
  public static String doGet(String url, Map<String, String> params, String submitEncoding,
      String resultEncoding) {
    String res = "";
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpGet httpGet = genHttpGet(url, params, submitEncoding);
      HttpEntity replyEntity = httpClient.execute(httpGet).getEntity();
      res = EntityUtils.toString(replyEntity, resultEncoding);
      return res;
    } catch (Exception e) {
      logError(url, UrlUtils.genParamsStr(params), res, e);
      throw new SysException("HttpClient调用失败", e);
    }
  }

  /**
   * 调用HttpPost请求。
   * 
   * @param url URL地址
   * @return 返回响应消息字符串。
   */
  public static String doPost(String url) {
    return doPost(url, null);
  }

  /**
   * 调用HttpPost请求。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @return 返回响应消息字符串。
   */
  public static String doPost(String url, Map<String, String> params) {
    return doPost(url, params, Encoding.UTF8);
  }

  /**
   * 调用HttpPost请求。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @param encoding 编码格式
   * @return 返回响应消息字符串。
   */
  public static String doPost(String url, Map<String, String> params, String encoding) {
    return doPost(url, params, encoding, encoding);
  }

  /**
   * 调用HttpPost请求。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @param submitEncoding 提交参数编码格式
   * @param resultEncoding 返回结果编码格式
   * @return 返回响应消息字符串。
   */
  public static String doPost(String url, Map<String, String> params, String submitEncoding,
      String resultEncoding) {
    String res = "";
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpPost httpPost = genHttpPost(url, params, submitEncoding);
      HttpEntity replyEntity = httpClient.execute(httpPost).getEntity();
      res = EntityUtils.toString(replyEntity, resultEncoding);
      return res;
    } catch (Exception e) {
      logError(url, UrlUtils.genParamsStr(params), res, e);
      throw new SysException("HttpClient调用失败", e);
    }
  }

  /**
   * 将请求参数Map转换成键值对列表。
   * 
   * @param params 请求参数
   * @return 返回请求参数键值对列表。
   */
  public static List<NameValuePair> mapToPairs(Map<String, String> params) {
    List<NameValuePair> pairs = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(params)) {
      for (Entry<String, String> param : params.entrySet()) {
        pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
      }
    }
    return pairs;
  }

  /**
   * 将键值对列表转换成请求参数Map。
   * 
   * @param pairs 键值对列表
   * @return 返回请求参数Map。
   */
  public static Map<String, String> pairsTomap(List<NameValuePair> pairs) {
    Map<String, String> params = new LinkedHashMap<>();
    if (CollectionUtils.isNotEmpty(pairs)) {
      for (NameValuePair pair : pairs) {
        params.put(pair.getName(), pair.getValue());
      }
    }
    return params;
  }

  /**
   * 记录调用错误日志。
   * 
   * @param url URL地址
   * @param req 请求消息
   * @param res 响应消息
   * @param e 异常信息
   */
  private static void logError(String url, String req, String res, Exception e) {
    String msg = String.format("[Http Client 调用失败：%s]%n[请求消息：%s]%n[响应消息：%s]%n[异常信息：%s]", url, req,
        res, e.getMessage());
    log.error(msg);
  }

  /**
   * 生成HttpGet请求对象。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @param encoding 编码格式
   * @return 返回HttpGet请求对象。
   * @throws URISyntaxException 抛出URISyntaxException异常
   */
  private static HttpGet genHttpGet(String url, Map<String, String> params, String encoding)
      throws URISyntaxException {
    URIBuilder builder = new URIBuilder(url);
    builder.addParameters(mapToPairs(params));
    builder.setCharset(Charset.forName(encoding));
    URI uri = builder.build();
    HttpGet httpGet = new HttpGet(uri);
    httpGet.setConfig(getDefaultRequestConfig());
    return httpGet;
  }

  /**
   * 生成HttpPost请求对象。
   * 
   * @param url URL地址
   * @param params 请求参数
   * @param encoding 编码格式
   * @return 返回HttpPost请求对象。
   * @throws UnsupportedEncodingException 抛出UnsupportedEncodingException异常
   */
  private static HttpPost genHttpPost(String url, Map<String, String> params, String encoding)
      throws UnsupportedEncodingException {
    HttpPost httpPost = new HttpPost(url);
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(mapToPairs(params), encoding);
    httpPost.setEntity(entity);
    httpPost.setConfig(getDefaultRequestConfig());
    return httpPost;
  }

  /**
   * 获取默认的请求配置。（连接超时5秒，Socket超时5秒，连接请求超时10秒）
   * 
   * @return 返回默认请求配置。
   */
  private static RequestConfig getDefaultRequestConfig() {
    return RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000)
        .setConnectionRequestTimeout(10000).build();
  }

  /**
   * 私有构造方法。
   */
  private HttpClientUtils() {}
}
