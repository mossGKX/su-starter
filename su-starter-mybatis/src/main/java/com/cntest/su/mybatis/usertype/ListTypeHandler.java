package com.cntest.su.mybatis.usertype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * List类型转换器。
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ListTypeHandler extends BaseTypeHandler<List<String>> {
  @Override
  public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return stringToList(rs.getString(columnName));
  }

  @Override
  public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return stringToList(rs.getString(columnIndex));
  }

  @Override
  public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return stringToList(cs.getString(columnIndex));
  }

  @Override
  public void setNonNullParameter(PreparedStatement preparedStatement, int columnIndex,
      List<String> parameter, JdbcType jdbcType) throws SQLException {
    if (CollectionUtils.isNotEmpty(parameter)) {
      preparedStatement.setString(columnIndex, StringUtils.join(parameter, ","));
    } else {
      preparedStatement.setString(columnIndex, "");
    }
  }

  /**
   * 字符串转成字符列表。
   * 
   * @param content 字符串内容
   * @return 返回转换后的字符列表。
   */
  private List<String> stringToList(String content) {
    if (StringUtils.isBlank(content)) {
      return new ArrayList<>();
    } else {
      String[] split = content.split(",");
      return CollectionUtils.toList(split);
    }
  }
}
