package com.cntest.iexam.pay.yee;

import lombok.Getter;

/**
 * 查询订单响应对象。
 */
@Getter
public class OrderRes extends AbstractYeeRes {
  /** 易宝支付交易流水号 */
  private String trxId;
  /** 支付金额 */
  private String amt;
  /** 交易币种 */
  private String cur;
  /** 商品名称 */
  private String pid;
  /** 商户订单号 */
  private String order;
  /** 商户扩展信息 */
  private String mp;
  /** 退款请求号 */
  private String refundRequestId;
  /** 订单创建时间 */
  private String createTime;
  /** 订单成功时间 */
  private String finishTime;
  /** 退款请求金额 */
  private String refundAmount;
  /** 订单支付状态 */
  private String payStatus;
  /** 退款次数 */
  private String refundCount;
  /** 已退款金额 */
  private String refundAmt;

  public OrderRes(String resStr, String key) {
    super(resStr, key);
  }

  @Override
  protected void setPrivateFields() {
    trxId = results.get("r2_TrxId");
    amt = results.get("r3_Amt");
    cur = results.get("r4_Cur");
    pid = results.get("r5_Pid");
    order = results.get("r6_Order");
    mp = results.get("r8_MP");
    refundRequestId = results.get("rw_RefundRequestID");
    createTime = results.get("rx_CreateTime");
    finishTime = results.get("ry_FinshTime");
    refundAmount = results.get("rz_RefundAmount");
    payStatus = results.get("rb_PayStatus");
    refundCount = results.get("rc_RefundCount");
    refundAmt = results.get("rd_RefundAmt");
  }
}
