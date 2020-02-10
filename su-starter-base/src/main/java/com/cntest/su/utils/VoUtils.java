package com.cntest.su.utils;

import java.util.ArrayList;
import java.util.List;

import com.cntest.su.exception.SysException;
import com.cntest.su.model.Page;

/**
 * VO、PO工具类。
 */
public class VoUtils {
  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @return 返回目标对象。
   */
  public static <T> T copy(Object source, Class<T> targetClass) {
    return copy(source, targetClass, null, null);
  }

  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @param excludeFields 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象。
   */
  public static <T> T copy(Object source, Class<T> targetClass, String excludeFields) {
    return copy(source, targetClass, null, excludeFields);
  }

  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field，但如果指定了要复制的Field，则为null时该Field也复制。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @param includeFields 要复制的Field的名称，多个名称之间用“,”分割
   * @param excludeFields 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象。
   */
  public static <T> T copy(Object source, Class<T> targetClass, String includeFields,
      String excludeFields) {
    try {
      T target = targetClass.newInstance();
      BeanUtils.copyFields(source, target, includeFields, excludeFields);
      return target;
    } catch (Exception e) {
      throw new SysException("复制对象时发生异常。", e);
    }
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copy(List<?> sourceList, Class<T> targetClass) {
    return copy(sourceList, targetClass, null, null);
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @param excludeFields 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copy(List<?> sourceList, Class<T> targetClass, String excludeFields) {
    return copy(sourceList, targetClass, null, excludeFields);
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field，但如果指定了要复制的Field，则为null时该Field也复制。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @param includeFields 要复制的Field的名称，多个名称之间用“,”分割
   * @param excludeFields 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copy(List<?> sourceList, Class<T> targetClass, String includeFields,
      String excludeFields) {
    List<T> targetList = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(sourceList)) {
      for (Object source : sourceList) {
        targetList.add(copy(source, targetClass, includeFields, excludeFields));
      }
    }
    return targetList;
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copy(Page<?> sourcePage, Class<T> targetClass) {
    return copy(sourcePage, targetClass, null, null);
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @param excludeFields 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copy(Page<?> sourcePage, Class<T> targetClass, String excludeFields) {
    return copy(sourcePage, targetClass, null, excludeFields);
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field，但如果指定了要复制的Field，则为null时该Field也复制。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @param includeFields 要复制的Field的名称，多个名称之间用“,”分割
   * @param excludeFields 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copy(Page<?> sourcePage, Class<T> targetClass, String includeFields,
      String excludeFields) {
    Page<T> targetPage =
        new Page<>(sourcePage.getCount(), sourcePage.getNumber(), sourcePage.getSize());
    targetPage
        .setContents(copy(sourcePage.getContents(), targetClass, includeFields, excludeFields));
    return targetPage;
  }

  private VoUtils() {}
}
