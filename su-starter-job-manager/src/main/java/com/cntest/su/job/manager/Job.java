package com.cntest.su.job.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cntest.su.job.manager.enums.BlockStrategy;
import com.cntest.su.job.manager.enums.RouteStrategy;
import com.cntest.su.utils.StringUtils;
import com.xxl.job.core.glue.GlueTypeEnum;

import lombok.Data;

/**
 * 任务。
 */
@Data
public class Job {
  /** ID */
  private Integer id;
  /** 执行器集群ID */
  private Integer workerGroupId;
  /** 描述 */
  private String desc = "";
  /** 定时任务表达式 */
  private String cron = "0 0 0 * * ? *";
  /** 负责人 */
  private String author = "admin";
  /** 告警邮件 */
  private String alarmEmail = "";
  /** 处理器 */
  private String handler;
  /** 路由策略 */
  private RouteStrategy routeStrategy = RouteStrategy.LEAST_RECENTLY_USED;
  /** 阻塞策略 */
  private BlockStrategy blockStrategy = BlockStrategy.SERIAL_EXECUTION;
  /** 参数 */
  private String params = "";
  /** 超时时间 */
  private Integer timeout = -1;
  /** 失败重试次数 */
  private Integer retry = -1;
  /** 子任务ID列表 */
  private List<String> childIds = new ArrayList<>();

  /**
   * 添加子任务ID。
   * 
   * @param childId 子任务ID
   */
  public void addChildId(String childId) {
    childIds.add(childId);
  }

  /**
   * 生成POST参数。
   * 
   * @return 返回生成的POST参数。
   */
  public Map<String, String> genPostParams() {
    Map<String, String> postParams = new HashMap<>();
    postParams.put("jobDesc", desc);
    postParams.put("jobGroup", workerGroupId.toString());
    postParams.put("jobCron", cron);
    postParams.put("author", author);
    postParams.put("alarmEmail", alarmEmail);
    postParams.put("executorRouteStrategy", routeStrategy.name());
    postParams.put("executorHandler", handler);
    postParams.put("executorParam", params);
    postParams.put("executorBlockStrategy", blockStrategy.name());
    postParams.put("executorTimeout", timeout.toString());
    postParams.put("executorFailRetryCount", retry.toString());
    postParams.put("glueType", GlueTypeEnum.BEAN.getDesc());
    postParams.put("childJobId", StringUtils.join(childIds, ","));
    return postParams;
  }
}
