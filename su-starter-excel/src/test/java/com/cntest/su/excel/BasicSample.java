package com.cntest.su.excel;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cntest.su.excel.model.Department;

public class BasicSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("department", Department.single());

    Excel excel = excelFactory.create("basic", model);
    excel.writeTo(new FileOutputStream(outputDir + "/basic_export.xlsx"));
  }
}
