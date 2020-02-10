package com.cntest.su.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日期区间查询条件模型。
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DateRangeQuery extends PageQuery {
  @ApiModelProperty(value = "起始日期")
  private Date startDate;
  @ApiModelProperty(value = "截止日期")
  private Date endDate;
}
