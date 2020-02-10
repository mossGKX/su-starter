package com.cntest.su.jpa.audit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;

import com.cntest.su.exception.SysException;
import com.cntest.su.jpa.audit.annotation.LogBean;
import com.cntest.su.jpa.audit.annotation.LogField;
import com.cntest.su.jpa.audit.model.LogData;
import com.cntest.su.jpa.entity.IdEntity;
import com.cntest.su.utils.BeanUtils;
import com.cntest.su.utils.DateUtils;
import com.cntest.su.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务日志。
 */
@Indexed
@Entity
@Table(name = "BizLog")
@Getter
@Setter
public class BizLog extends IdEntity {
  /** 操作人 */
  @Field(analyze = Analyze.NO)
  private String operator;
  /** 操作时间 */
  @Field(analyze = Analyze.NO)
  @SortableField
  @Temporal(TemporalType.TIMESTAMP)
  private Date operateTime;
  /** 实体ID */
  private String entityId;
  /** 日志信息 */
  @Field(analyze = Analyze.NO)
  private String message;
  /** 原数据 */
  private String origData;
  /** 新数据 */
  private String newData;

  /**
   * 判断是否记录了业务数据。
   * 
   * @return 如果记录了业务数据返回true，否则返回false。
   */
  public Boolean hasData() {
    return StringUtils.isNotEmpty(origData) || StringUtils.isNotEmpty(newData);
  }

  /**
   * 输出日志数据项。用于原数据与新数据比较。
   * 
   * @return 返回日志数据项列表。
   */
  public List<LogData> toLogData() {
    List<LogData> datas = new ArrayList<>();
    fillInOrigData(datas);
    fillInNewData(datas);
    return datas;
  }

  /**
   * 设置原数据。
   * 
   * @param origDataObject 原数据对象
   */
  public void setOrigData(Object origDataObject) {
    this.origData = getJson(origDataObject);
  }

  /**
   * 设置新数据。
   * 
   * @param newDataObject 新数据对象
   */
  public void setNewData(Object newDataObject) {
    this.newData = getJson(newDataObject);
  }

  /**
   * 填充源数据列表。
   * 
   * @param datas 数据列表
   */
  @SuppressWarnings("unchecked")
  private void fillInOrigData(List<LogData> datas) {
    if (origData != null) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> dataMap = mapper.readValue(origData, LinkedHashMap.class);
        if (datas.isEmpty()) {
          for (Entry<String, String> entry : dataMap.entrySet()) {
            LogData data = new LogData();
            data.setText(entry.getKey());
            data.setOrigData(entry.getValue());
            datas.add(data);
          }
        } else {
          for (LogData data : datas) {
            data.setOrigData(dataMap.get(data.getText()));
          }
        }
      } catch (Exception e) {
        throw new SysException("填充日志数据原值时发生异常。", e);
      }
    }
  }

  /**
   * 填充新数据列表。
   * 
   * @param datas 新数据列表。
   */
  @SuppressWarnings("unchecked")
  private void fillInNewData(List<LogData> datas) {
    if (newData != null) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> dataMap = mapper.readValue(newData, LinkedHashMap.class);
        if (datas.isEmpty()) {
          for (Entry<String, String> entry : dataMap.entrySet()) {
            LogData data = new LogData();
            data.setText(entry.getKey());
            data.setNewData(entry.getValue());
            datas.add(data);
          }
        } else {
          for (LogData data : datas) {
            data.setNewData(dataMap.get(data.getText()));
          }
        }
      } catch (Exception e) {
        throw new SysException("填充日志数据新值时发生异常。", e);
      }
    }
  }

  /**
   * 根据数据对象中的日志注解生成数据对象的日志Json字符串。
   * 
   * @param data 数据对象
   * @return 返回日志Json字符串。
   */
  private String getJson(Object data) {
    try {
      Map<String, String> datas = new LinkedHashMap<>();
      datas.putAll(getAllLogFieldDatas(data));
      datas.putAll(getAllLogBeanDatas(data));
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(datas);
    } catch (Exception e) {
      throw new SysException("转换日志对象时发生异常。", e);
    }
  }

  /**
   * 获取所有日志字段的日志数据。
   * 
   * @param data 数据对象
   * @return 返回所有日志字段的日志数据。
   */
  private Map<String, String> getAllLogFieldDatas(Object data) {
    Map<String, String> datas = new LinkedHashMap<>();
    List<java.lang.reflect.Field> fields = BeanUtils.findField(data.getClass(), LogField.class);
    for (java.lang.reflect.Field field : fields) {
      LogField logField = field.getAnnotation(LogField.class);
      Object fieldValue = BeanUtils.getField(data, field);
      datas.putAll(getLogFieldData(fieldValue, logField));
    }
    return datas;
  }

  /**
   * 获取所有日志对象的日志数据。
   * 
   * @param data 数据对象
   * @return 返回所有日志对象的日志数据。
   */
  private Map<String, String> getAllLogBeanDatas(Object data) {
    Map<String, String> datas = new LinkedHashMap<>();
    List<java.lang.reflect.Field> fields = BeanUtils.findField(data.getClass(), LogBean.class);
    for (java.lang.reflect.Field field : fields) {
      LogBean logBean = field.getAnnotation(LogBean.class);
      Object fieldValue = BeanUtils.getField(data, field);
      datas.putAll(getLogBeanDatas(fieldValue, logBean));
    }
    return datas;
  }

  /**
   * 获取日志字段的日志数据。
   * 
   * @param fieldValue 日志字段
   * @param logField 日志字段注解
   * @return 返回日志字段的日志数据。
   */
  private Map<String, String> getLogFieldData(Object fieldValue, LogField logField) {
    Map<String, String> datas = new LinkedHashMap<>();
    String value = "";
    if (fieldValue != null) {
      value = fieldValue.toString();
      if (fieldValue instanceof Date) {
        value = DateUtils.format((Date) fieldValue, logField.format());
      }
    }
    datas.put(logField.text(), value);
    return datas;
  }

  /**
   * 获取日志对象的日志数据。
   * 
   * @param bean 日志对象
   * @param logBean 日志对象注解
   * @return 返回日志对象的日志数据。
   */
  private Map<String, String> getLogBeanDatas(Object bean, LogBean logBean) {
    Map<String, String> datas = new LinkedHashMap<>();
    if (bean != null) {
      for (LogField logField : logBean.value()) {
        Object fieldValue = BeanUtils.getField(bean, logField.property());
        datas.putAll(getLogFieldData(fieldValue, logField));
      }
    } else {
      for (LogField logField : logBean.value()) {
        datas.putAll(getLogFieldData(null, logField));
      }
    }
    return datas;
  }
}
