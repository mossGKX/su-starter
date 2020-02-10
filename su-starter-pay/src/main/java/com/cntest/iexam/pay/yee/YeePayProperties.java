package com.cntest.iexam.pay.yee;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("su.pay.yee")
public class YeePayProperties {
  /** node请求地址 */
  private String nodeUrl = "https://www.yeepay.com/app-merchant-proxy/node";
  /** command请求地址 */
  private String commandUrl = "https://cha.yeepay.com/app-merchant-proxy/command";
  /** 支付回调页面地址 */
  private String returnUrl;
  /** 支付回调通知地址 */
  private String notifyUrl;
}
