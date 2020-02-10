package com.cntest.iexam.pay.yee;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import lombok.Getter;

/**
 * 支付响应对象。
 */
@Getter
public class PayRes extends AbstractYeeRes {
  /** 商户编号 */
  private String merId;
  /** 易宝交易流水号 */
  private String trxId;
  /** 支付金额 */
  private String amt;
  /** 交易币种 */
  private String cur;
  /** 商品名称 */
  private String pid;
  /** 商户订单号 */
  private String order;
  /** 易宝支付会员 */
  private String uid;
  /** 商户扩展信息 */
  private String mp;
  /** 通知类型 */
  private String btype;
  /** 支付通道编码 */
  private String bankId;
  /** 银行订单号 */
  private String bankOrderId;
  /** 支付成功时间 */
  private String payDate;
  /** 神州行充值卡号 */
  private String cardNo;
  /** 通知时间 */
  private String trxtime;
  /** 用户手续费 */
  private String sourceFee;
  /** 商户手续费 */
  private String targetFee;

  public PayRes(String resStr, String key) {
    super(resStr, key);
  }

  @Override
  protected void setPrivateFields() {
    merId = results.get("p1_MerId");
    trxId = results.get("r2_TrxId");
    amt = results.get("r3_Amt");
    cur = results.get("r4_Cur");
    pid = results.get("r5_Pid");
    order = results.get("r6_Order");
    uid = results.get("r7_Uid");
    mp = results.get("r8_MP");
    btype = results.get("r9_BType");

    bankId = results.remove("rb_BankId");
    bankOrderId = results.remove("ro_BankOrderId");
    payDate = results.remove("rp_PayDate");
    cardNo = results.remove("rq_CardNo");
    trxtime = results.remove("ru_Trxtime");
    sourceFee = results.remove("rq_SourceFee");
    targetFee = results.remove("rq_TargetFee");
  }

  @Override
  protected Collection<String> getSignValues() {
    Map<String, String> newResults = new TreeMap<>();
    newResults.putAll(results);
    return newResults.values();
  }
}
