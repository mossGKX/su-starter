package com.cntest.su.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;

import com.cntest.su.utils.BeanUtils;
import com.cntest.su.utils.CollectionUtils;

import lombok.Data;

/**
 * Excel数据。
 */
@Data
public class ExcelData {
  /** 模版名称 */
  private String templateName;
  /** 工作表数据模型列表 */
  private List<?> sheetModels;
  /** 工作表名称列表 */
  private List<String> sheetNames = new ArrayList<>();
  /** 工作表名称字段 */
  private String sheetNameField = "name";
  /** 工作表数据模型名称 */
  private String sheetModelName = "sheetModels";
  /** 除工作表模型外的数据模型 */
  private Map<String, Object> otherModel;
  /** 多工作表开始位置 */
  private Integer startSheetNum = 0;

  public Context toContext() {
    Context context = new Context();
    if (CollectionUtils.isNotEmpty(otherModel)) {
      context = new Context(otherModel);
    }
    context.putVar(sheetModelName, sheetModels);
    if (sheetNames.isEmpty()) {
      for (Object sheetModel : sheetModels) {
        sheetNames.add(BeanUtils.getField(sheetModel, sheetNameField).toString());
      }
    }
    context.putVar("sheetNames", sheetNames);
    return context;
  }
}
