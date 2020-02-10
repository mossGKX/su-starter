package com.cntest.su.word;

import java.io.FileOutputStream;

import org.junit.Test;

import com.cntest.su.word.model.Software;

public class SoftwareSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    Word excel = wordFactory.create("software", new Software());
    excel.writeTo(new FileOutputStream(outputDir + "/software_export.docx"));
  }
}
