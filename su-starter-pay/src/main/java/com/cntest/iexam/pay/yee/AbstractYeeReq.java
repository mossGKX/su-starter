package com.cntest.iexam.pay.yee;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求基类。
 */
@Getter
@Setter
public abstract class AbstractYeeReq {
  /** 业务类型 */
  protected String cmd;
  /** 商户编号 */
  protected String merId;
  /** 签名 */
  protected String hmac;
  /** 安全签名 */
  protected String hmacSafe;

  protected abstract Map<String, String> toMap();

  public Map<String, String> toSignMap(String key) {
    Map<String, String> map = toMap();
    hmac = YeePayUtils.sign(map.values(), key);
    hmacSafe = YeePayUtils.signSafe(map.values(), key);
    map.put("hmac", hmac);
    map.put("hmac_safe", hmacSafe);
    return map;
  }
}
