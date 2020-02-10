package com.cntest.su.mybatis.usertype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json转换器。
 */
@MappedTypes(Object.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonTypeHandler<T extends Object> extends BaseTypeHandler<T> {
  private ObjectMapper mapper = new ObjectMapper();
  private Class<T> clazz;

  public JsonTypeHandler(Class<T> clazz) {
    if (clazz == null)
      throw new IllegalArgumentException("Type argument cannot be null");
    this.clazz = clazz;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, this.toJson(parameter));
  }

  @Override
  public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return this.toObject(rs.getString(columnName));
  }

  @Override
  public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return this.toObject(rs.getString(columnIndex));
  }

  @Override
  public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return this.toObject(cs.getString(columnIndex));
  }

  private String toJson(T object) throws SQLException {
    try {
      return mapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new SQLException("转换目标对象为Json时发生异常。", e);
    }
  }

  private T toObject(String content) throws SQLException {
    if (content != null && !content.isEmpty()) {
      try {
        return mapper.readValue(content, clazz);
      } catch (Exception e) {
        throw new SQLException("转换目标对象为Json时发生异常。", e);
      }
    } else {
      return null;
    }
  }
}
