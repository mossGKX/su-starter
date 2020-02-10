package com.cntest.su.mybatis.usertype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 数组转换器。
 */
@MappedTypes(String[].class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ArrayTypeHandler extends BaseTypeHandler<String[]> {
  @Override
  public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return stringToArray(rs.getString(columnName));
  }

  @Override
  public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return stringToArray(rs.getString(columnIndex));
  }

  @Override
  public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return stringToArray(cs.getString(columnIndex));
  }

  @Override
  public void setNonNullParameter(PreparedStatement preparedStatement, int columnIndex,
      String[] parameter, JdbcType jdbcType) throws SQLException {
    if (CollectionUtils.isNotEmpty(parameter)) {
      preparedStatement.setString(columnIndex, StringUtils.join(parameter, ","));
    } else {
      preparedStatement.setString(columnIndex, "");
    }
  }

  /**
   * 字符串转换成字符数组。
   * 
   * @param content 字符串内容
   * @return 返回转换的字符数组。
   */
  private String[] stringToArray(String content) {
    if (StringUtils.isBlank(content)) {
      return new String[0];
    } else {
      return content.split(",");
    }
  }
}
