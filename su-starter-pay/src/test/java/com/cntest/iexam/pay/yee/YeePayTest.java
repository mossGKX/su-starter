package com.cntest.iexam.pay.yee;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cntest.iexam.pay.config.PayAutoConfiguration;

/**
 * @Author: yuantt
 * @Date: ${date}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = PayAutoConfiguration.class)
public class YeePayTest {
  public static final String account="10012442782";
  public static final String key ="mP42238826nuW64r7yh26DGK34o2L2m81L25RG32lD7Lo1058A7iJ28at6QS";

  @Autowired
  private YeePay yeePay;

  @Test
  public void testGenPayUrl() throws Exception {
    String destUrl =
        "https://www.yeepay.com/app-merchant-proxy/node?p0_Cmd=Buy&p1_MerId=10012442782&p2_Order=201807241338513851672&p3_Amt=0.01&p4_Cur=CNY&p5_Pid=%BD%AD%CE%F7%CA%A12018%C4%EA%D6%D0%D0%A1%D1%A7%BD%CC%CA%A6%D5%D0%C6%B8%BF%BC%CA%D4&p6_Pcat=&p7_Pdesc=&p8_Url=http%3A%2F%2F2e3a285a.ngrok.io%2FapplyFee%2FpaySuccess&p9_SAF=&pa_MP=%C1%CE%CE%B0-%BD%AD%CE%F7%CA%A12018%C4%EA%D6%D0%D0%A1%D1%A7%BD%CC%CA%A6%D5%D0%C6%B8%BF%BC%CA%D4&pb_ServerNotifyUrl=http%3A%2F%2F2e3a285a.ngrok.io%2Fpay%2FgetYeepayResult&pd_FrpId=&pm_Period=180&pn_Unit=day&pr_NeedResponse=&pt_UserName=&pt_PostalCode=&pt_Address=&pt_TeleNo=&pt_Mobile=&pt_Email=&pt_LeaveMessage=&hmac=98f14c70c71ad0057dbcacca46de63ad&hmac_safe=be0ef2cccb4e207e779db15cf213b018";
    PayReq req = new PayReq();
    req.setOrder("201807241338513851672");
    req.setAmt("0.01");
    String projectName = "江西省2018年中小学教师招聘考试";
    String userName = "廖伟";
    String paMp = userName + "-" + projectName;
    req.setPid(projectName);
    req.setMp(paMp);
    String pm_Period = "180";
    String pn_Unit = "day";
    req.setPeriod(pm_Period);
    req.setUnit(pn_Unit);
    String payUrl = yeePay.genPayUrl(req,account,key);

    Assert.assertEquals(destUrl, payUrl);
  }


  @Test
  public void testGenPayRes() throws Exception {
    String queryString =
        "p1_MerId=10012442782&r1_Code=1&r2_TrxId=912626198291703I&r3_Amt=0.04&r4_Cur=RMB&r5_Pid=2018%C4%EA%B6%C8%C8%AB%B9%FA%B7%BF%B5%D8%B2%FA%B9%C0%BC%DB%CA%A6%BF%BC%CA%D4&r6_Order=201807231457115711918&r7_Uid=&r8_MP=%D5%C5%B6%FE-2018%C4%EA%B6%C8%C8%AB%B9%FA%B7%BF%B5%D8%B2%FA%B9%C0%BC%DB%CA%A6%BF%BC%CA%D4&r9_BType=2&rb_BankId=YJZF-NET&ro_BankOrderId=18080116471080668&rp_PayDate=20180801160012&rq_CardNo=&ru_Trxtime=20180801160013&rq_SourceFee=0.00&rq_TargetFee=0.00&hmac_safe=37317b8419456c8a4afd25dc72201299&hmac=82bdd6da286b51eb5a7618c68abd08d2&r0_Cmd=Buy";
    PayRes payRes = yeePay.genPayRes(queryString,key);
    Assert.assertNotNull(payRes);
  }

  @Test
  public void testQueryOrder() throws Exception {
    OrderReq req = new OrderReq();
    req.setOrder("201807241338513851672");
    OrderRes res = yeePay.queryOrder(req,account,key);
    Assert.assertNotNull(res);
  }
}
