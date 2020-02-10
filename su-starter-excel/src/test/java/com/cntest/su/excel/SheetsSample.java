package com.cntest.su.excel;

import java.io.FileOutputStream;

import org.junit.Test;

import com.cntest.su.excel.model.Department;

public class SheetsSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    ExcelData data = new ExcelData();
    data.setSheetModelName("departments");
    data.setSheetModels(Department.multi(3));

    Excel excel = excelFactory.create("sheets", data);
    excel.writeTo(new FileOutputStream(outputDir + "/sheets_export.xlsx"));
  }
}
