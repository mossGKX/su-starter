package com.cntest.su.excel;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.cntest.su.excel.config.ExcelProperties;
import com.cntest.su.exception.SysException;
import com.cntest.su.utils.ResourceUtils;
import com.cntest.su.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Excel组件工厂。
 */
@Slf4j
public class ExcelFactory {
  @Autowired
  private ExcelProperties properties;
  private Map<String, Resource> templates = new HashMap<>();

  @PostConstruct
  protected void init() {
    String resourcePath = properties.getTemplateDir() + "/**.xlsx";
    for (Resource resource : ResourceUtils.getResourcesByWildcard(resourcePath)) {
      String templateName = StringUtils.substringBefore(resource.getFilename(), ".xlsx");
      templates.put(templateName, resource);
      log.info("加载Excel模版[{}]。", templateName);
    }
  }

  /**
   * 创建一个单工作表的Excel组件。
   * 
   * @param templateName 模版名称
   * @param context 数据模型
   * @return 返回一个单工作表的Excel组件。
   */
  public Excel create(String templateName, Context context) {
    try {
      InputStream in = templates.get(templateName).getInputStream();
      PoiTransformer transformer = PoiTransformer.createTransformer(in);
      AreaBuilder areaBuilder = new XlsCommentAreaBuilder();
      areaBuilder.setTransformer(transformer);
      List<Area> xlsAreaList = areaBuilder.build();
      for (Area xlsArea : xlsAreaList) {
        xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);
        xlsArea.processFormulas();
      }
      Excel excel = new Excel(transformer.getWorkbook());
      in.close();
      return excel;
    } catch (Exception e) {
      throw new SysException("创建Excel组件时发生异常。", e);
    }
  }

  /**
   * 创建一个单工作表的Excel组件。
   * 
   * @param templateName 模版名称
   * @param model 数据模型
   * @return 返回一个单工作表的Excel组件。
   */
  public Excel create(String templateName, Map<String, Object> model) {
    return create(templateName, new Context(model));
  }

  /**
   * 创建一个多工作表的Excel组件。
   *
   * @param templateName 模版名称
   * @param data Excel数据
   * @return 返回一个多工作表的Excel组件。
   */
  public Excel create(String templateName, ExcelData data) {
    Excel excel = create(templateName, data.toContext());
    excel.getWorkbook().removeSheetAt(data.getStartSheetNum());
    return excel;
  }
}
