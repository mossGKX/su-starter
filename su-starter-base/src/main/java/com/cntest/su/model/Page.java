package com.cntest.su.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页对象。
 * 
 * @param <T> 分页对象中包含内容的对象类型
 */
@Data
public class Page<T> {
  @ApiModelProperty(value = "是否第一页")
  private Boolean first = true;
  @ApiModelProperty(value = "是否最后一页")
  private Boolean last = true;
  @ApiModelProperty(value = "总页数")
  private Integer pageCount = 0;
  @ApiModelProperty(value = "总记录数")
  private Integer count = 0;
  @ApiModelProperty(value = "下一页页码")
  private Integer next = 1;
  @ApiModelProperty(value = "上一页页码")
  private Integer previous = 1;
  @ApiModelProperty(value = "每页记录数")
  private Integer size;
  @ApiModelProperty(value = "当前页码")
  private Integer number = 1;
  @ApiModelProperty(value = "分页记录集合")
  private List<T> contents = new ArrayList<>();

  /**
   * 初始化一个新的分页对象，该构造方法通常用于生成一个空的分页对象。
   * 
   * @param pageSize 每页记录数
   */
  public Page(Integer pageSize) {
    size = pageSize;
  }

  /**
   * 通过指定记录总数、当前页数、每页记录数来构造一个分页对象。
   * 
   * 
   * @param recordCount 记录总数
   * @param pageNum 当前页数
   * @param pageSize 每页记录数
   */
  public Page(Integer recordCount, Integer pageNum, Integer pageSize) {
    count = recordCount;
    size = pageSize;
    pageCount = count % size > 0 ? count / size + 1 : count / size;
    number = pageCount < pageNum ? pageCount : pageNum;
    first = number <= 1 ? true : false;
    previous = number <= 1 ? number : number - 1;
    last = number >= pageCount ? true : false;
    next = number >= pageCount ? number : number + 1;
    contents = new ArrayList<>();
  }
}
