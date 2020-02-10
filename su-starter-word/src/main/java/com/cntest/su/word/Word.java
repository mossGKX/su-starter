package com.cntest.su.word;

import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.cntest.su.exception.SysException;

import lombok.Getter;

/**
 * Word组件。
 */
public class Word {
  @Getter
  private XWPFDocument doc;

  public Word(XWPFDocument doc) {
    this.doc = doc;
  }

  /**
   * 将Word文件写入到输出流。
   * 
   * @param out 输出流
   */
  public void writeTo(OutputStream out) {
    try {
      doc.write(out);
      out.flush();
      out.close();
    } catch (Exception e) {
      throw new SysException("将Word文件写入到输出流时发生异常。", e);
    }
  }
}
