package com.cntest.su.mybatis.utils;

import java.util.List;

import com.cntest.su.model.Page;
import com.github.pagehelper.PageInfo;

/**
 * 分页工具。
 */
public class PageUtils {
  /**
   * 将PageInfo转换成Page对象。
   * 
   * @param pageInfo PageInfo对象
   * @return 返回转换后的Page对象。
   */
  public static <T> Page<T> toPage(PageInfo<T> pageInfo) {
    Long total = pageInfo.getTotal();
    if (total > 0) {
      Page<T> page = new Page<>(total.intValue(), pageInfo.getPageNum(), pageInfo.getPageSize());
      page.setContents(pageInfo.getList());
      return page;
    } else {
      return new Page<>(pageInfo.getPageSize());
    }
  }

  public static <T> Page<T> toPage(List<T> list) {
    return toPage(new PageInfo<>(list));
  }

  private PageUtils() {}
}
