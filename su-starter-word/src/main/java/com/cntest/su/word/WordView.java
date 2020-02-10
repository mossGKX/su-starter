package com.cntest.su.word;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.servlet.view.AbstractView;

import com.cntest.su.constant.Encoding;
import com.cntest.su.utils.UrlUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Word视图。
 */
@Slf4j
public class WordView extends AbstractView {
  private String fileName;
  private Word word;

  public WordView(String fileName, Word word) {
    setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    this.fileName = fileName;
    this.word = word;
  }

  @Override
  protected boolean generatesDownloadContent() {
    return true;
  }

  @Override
  protected final void renderMergedOutputModel(Map<String, Object> model,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    String docName = fileName + ".docx";
    response.setHeader("content-disposition", "attachment;filename=" + UrlUtils.encode(docName));
    response.setCharacterEncoding(Encoding.UTF8);
    response.setContentType(getContentType());
    XWPFDocument document = word.getDoc();
    renderXWPFDocument(document, response);
  }

  protected void renderXWPFDocument(XWPFDocument document, HttpServletResponse response)
      throws IOException {
    try {
      ServletOutputStream out = response.getOutputStream();
      document.write(out);
      document.close();
    } catch (OpenXML4JRuntimeException e) {
      log.debug("这里触发了POI一个未解决的Bug，不影响导出。");
    }
  }
}
