package com.cntest.iexam.pay.yee;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.cntest.su.constant.Encoding;
import com.cntest.su.exception.SysException;
import com.cntest.su.utils.HttpClientUtils;

import lombok.Getter;

/**
 * 响应基类。
 */
@Getter
public abstract class AbstractYeeRes {
  /** 响应结果集 */
  protected Map<String, String> results;
  /** 业务类型 */
  protected String cmd;
  /** 结果编码 */
  protected String code;
  /** 签名 */
  protected String hmac;
  /** 安全签名 */
  protected String hmacSafe;

  public AbstractYeeRes(String resStr, String key) {
    String result = resStr.replaceAll("\\n", "&");
    List<NameValuePair> pairs = URLEncodedUtils.parse(result, Charset.forName(Encoding.GBK));
    results = HttpClientUtils.pairsTomap(pairs);
    setPublicFields();
    setPrivateFields();
    verify(key);
  }

  /**
   * 设置私有属性，每个子类自己实现私有属性的设值。
   */
  protected abstract void setPrivateFields();

  /**
   * 获取签名值的集合。
   * 
   * @return 返回签名值集合。
   */
  protected Collection<String> getSignValues() {
    return results.values();
  }

  /**
   * 设置公共属性。
   */
  private void setPublicFields() {
    cmd = results.get("r0_Cmd");
    code = results.get("r1_Code");

    hmac = results.remove("hmac");
    hmacSafe = results.remove("hmac_safe");
  }

  /**
   * 对响应结果进行签名验证。
   * 
   * @param key 密钥
   */
  private void verify(String key) {
    if (!YeePayUtils.verify(getSignValues(), hmac, hmacSafe, key)) {
      throw new SysException("易宝支付验证签名失败，响应结果集为" + results);
    }
  }
}
