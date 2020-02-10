package com.cntest.su.excel;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.cntest.su.constant.Encoding;
import com.cntest.su.utils.UrlUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Excel视图。
 */
@Slf4j
public class ExcelView extends AbstractXlsxView {
  private String fileName;
  private Excel excel;

  public ExcelView(String fileName, Excel excel) {
    super();
    this.fileName = fileName;
    this.excel = excel;
  }

  @Override
  protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
    return excel.getWorkbook();
  }

  @Override
  protected void renderWorkbook(Workbook workbook, HttpServletResponse response)
      throws IOException {
    try {
      ServletOutputStream out = response.getOutputStream();
      workbook.write(out);
    } catch (OpenXML4JRuntimeException e) {
      log.debug("这里触发了POI一个未解决的Bug，不影响导出。");
    }
  }

  @Override
  protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    String excelName = fileName + ".xlsx";
    response.setHeader("content-disposition", "attachment;filename=" + UrlUtils.encode(excelName));
    response.setCharacterEncoding(Encoding.UTF8);
  }
}
