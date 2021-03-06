package com.cntest.su.jpa.usertype;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import com.cntest.su.jpa.entity.UuidEntity;
import com.cntest.su.utils.StringUtils;

/**
 * UuidEntity列表自定义类型。
 */
public class UuidEntityListUserType extends AbstractListUserType {
  @Override
  @SuppressWarnings("unchecked")
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
      Object owner) throws SQLException {
    try {
      String value = getValue(rs, names[0], session);

      if (StringUtils.isNotBlank(value)) {
        Field uuidEntityListField = getField(rs, names[0], owner);
        ParameterizedType parameterizedType =
            (ParameterizedType) uuidEntityListField.getGenericType();
        Class<? extends UuidEntity> uuidEntityClass =
            (Class<? extends UuidEntity>) parameterizedType.getActualTypeArguments()[0];
        List<Object> entities = new ArrayList<>();
        for (String entityId : value.split(",")) {
          entities.add(((Session) session).get(uuidEntityClass, entityId));
        }
        return entities;
      } else {
        return new ArrayList<UuidEntity>();
      }
    } catch (Exception e) {
      throw new HibernateException("转换UuidEntity列表类型时发生异常。", e);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void nullSafeSet(PreparedStatement st, Object value, int index,
      SharedSessionContractImplementor session) throws SQLException {
    try {
      if (value != null) {
        List<UuidEntity> entities = (List<UuidEntity>) value;
        List<String> uuidEntityIds = new ArrayList<>();
        for (UuidEntity entity : entities) {
          uuidEntityIds.add(entity.getId());
        }
        if (!uuidEntityIds.isEmpty()) {
          setValue(st, StringUtils.join(uuidEntityIds, ","), index, session);
        } else {
          setValue(st, null, index, session);
        }
      } else {
        setValue(st, null, index, session);
      }
    } catch (Exception e) {
      throw new HibernateException("转换UuidEntity列表类型时发生异常。", e);
    }
  }
}
