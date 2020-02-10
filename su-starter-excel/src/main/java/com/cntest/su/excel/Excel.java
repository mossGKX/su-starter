package com.cntest.su.excel;

import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;

import com.cntest.su.exception.SysException;

import lombok.Getter;

/**
 * Excel组件。
 */
public class Excel {
  @Getter
  private Workbook workbook;

  public Excel(Workbook workbook) {
    this.workbook = workbook;
  }

  /**
   * 将Excel文件写入到输出流。
   * 
   * @param out 输出流
   */
  public void writeTo(OutputStream out) {
    try {
      workbook.write(out);
      out.flush();
      out.close();
    } catch (Exception e) {
      throw new SysException("将Excel文件写入到输出流时发生异常。", e);
    }
  }
}
