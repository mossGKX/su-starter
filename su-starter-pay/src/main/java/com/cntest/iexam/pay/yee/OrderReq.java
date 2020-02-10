package com.cntest.iexam.pay.yee;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询订单请求对象。
 */
@Getter
@Setter
public class OrderReq extends AbstractYeeReq {
  /** 商户订单号 */
  private String order;
  /** 版本号 */
  private String ver = "3.0";
  /** 查询类型 */
  private String serviceType = "2";

  public OrderReq() {
    cmd = "QueryOrdDetail";
  }

  @Override
  protected Map<String, String> toMap() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("p0_Cmd", cmd);
    map.put("p1_MerId", merId);
    map.put("p2_Order", order);
    map.put("pv_Ver", ver);
    map.put("p3_ServiceType", serviceType);
    return map;
  }
}
