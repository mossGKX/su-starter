package com.cntest.su.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于分页/全文检索的查询条件模型。<br>
 * 更多条件的查询条件模型可继承该类进行定义。
 */
@Data
public class PageQuery {
  @ApiModelProperty(value = "当前页码", example = "1")
  @NotNull
  @Min(1)
  protected Integer pageNum = 1;
  @ApiModelProperty(value = "每页记录数", example = "20")
  @NotNull
  @Min(1)
  protected Integer pageSize = 20;
  @ApiModelProperty(value = "排序字段")
  protected String orderBy;
  @ApiModelProperty(value = "排序顺序")
  protected String sort;
  @ApiModelProperty(value = "模糊搜索关键字")
  protected String keyword;
}
