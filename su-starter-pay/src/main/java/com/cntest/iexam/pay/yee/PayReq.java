package com.cntest.iexam.pay.yee;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付请求对象。
 */
@Getter
@Setter
public class PayReq extends AbstractYeeReq {
  /** 商户订单号 */
  private String order = "";
  /** 支付金额 */
  private String amt = "";
  /** 交易币种 */
  private String cur = "CNY";
  /** 商品名称 */
  private String pid = "";
  /** 商品种类 */
  private String pcat = "";
  /** 商品描述 */
  private String pdesc = "";
  /** 回调地址 */
  private String url = "";
  /** 送货地址 */
  private String saf = "";
  /** 商户扩展信息 */
  private String mp = "";
  /** 服务器通知地址 */
  private String serverNotifyUrl = "";
  /** 支付通道编码 */
  private String frpId = "";
  /** 订单有效期 */
  private String period = "";
  /** 订单有效期单位 */
  private String unit = "";
  /** 应答机制 */
  private String needResponse = "";
  /** 考生/用户姓名 */
  private String userName = "";
  /** 身份证号 */
  private String postalCode = "";
  /** 地区 */
  private String address = "";
  /** 报考序号/银行卡号 */
  private String teleNo = "";
  /** 手机号 */
  private String mobile = "";
  /** 邮箱地址 */
  private String email = "";
  /** 用户标识 */
  private String leaveMessage = "";

  public PayReq() {
    cmd = "Buy";
  }

  @Override
  protected Map<String, String> toMap() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("p0_Cmd", cmd);
    map.put("p1_MerId", merId);
    map.put("p2_Order", order);
    map.put("p3_Amt", amt);
    map.put("p4_Cur", cur);
    map.put("p5_Pid", pid);
    map.put("p6_Pcat", pcat);
    map.put("p7_Pdesc", pdesc);
    map.put("p8_Url", url);
    map.put("p9_SAF", saf);
    map.put("pa_MP", mp);
    map.put("pb_ServerNotifyUrl", serverNotifyUrl);
    map.put("pd_FrpId", frpId);
    map.put("pm_Period", period);
    map.put("pn_Unit", unit);
    map.put("pr_NeedResponse", needResponse);
    map.put("pt_UserName", userName);
    map.put("pt_PostalCode", postalCode);
    map.put("pt_Address", address);
    map.put("pt_TeleNo", teleNo);
    map.put("pt_Mobile", mobile);
    map.put("pt_Email", email);
    map.put("pt_LeaveMessage", leaveMessage);
    return map;
  }
}
