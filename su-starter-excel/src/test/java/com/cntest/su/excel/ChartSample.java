package com.cntest.su.excel;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cntest.su.excel.model.Employee;

public class ChartSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("employees", Employee.multi(6));

    Excel excel = excelFactory.create("chart", model);
    excel.writeTo(new FileOutputStream(outputDir + "/chart_export.xlsx"));
  }
}
