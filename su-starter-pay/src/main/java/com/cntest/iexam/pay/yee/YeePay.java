package com.cntest.iexam.pay.yee;

import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.constant.Encoding;
import com.cntest.su.utils.HttpClientUtils;
import com.cntest.su.utils.UrlUtils;

/**
 * 易宝支付组件。
 */
public class YeePay {
  @Autowired
  private YeePayProperties properties;

  /**
   * 生成支付链接。
   * 
   * @param req 支付请求
   * @return 返回支付链接。
   */
  public String genPayUrl(PayReq req, String account, String key) {
    req.setMerId(account);
    req.setUrl(properties.getReturnUrl());
    req.setServerNotifyUrl(properties.getNotifyUrl());

    String payUrl = properties.getNodeUrl();
    payUrl += "?" + UrlUtils.genParamsStr(req.toSignMap(key), Encoding.GBK);
    return payUrl;
  }

  /**
   * 生成支付响应。
   * 
   * @param queryString 支付回调请求参数字符串
   * @return 返回支付响应。
   */
  public PayRes genPayRes(String queryString, String key) {
    return new PayRes(queryString, key);
  }

  /**
   * 查询单笔订单。
   * 
   * @param req 查询订单请求对象
   * @return 返回查询订单响应对象。
   */
  public OrderRes queryOrder(OrderReq req, String account, String key) {
    req.setMerId(account);
    String resStr = HttpClientUtils.doGet(properties.getCommandUrl(), req.toSignMap(key));
    return new OrderRes(resStr, key);
  }
}
