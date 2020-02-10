package com.cntest.su.word;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.cntest.su.exception.SysException;
import com.cntest.su.utils.ResourceUtils;
import com.cntest.su.utils.StringUtils;
import com.cntest.su.word.config.WordProperties;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

import lombok.extern.slf4j.Slf4j;

/**
 * Word组件工厂。
 */
@Slf4j
public class WordFactory {
  @Autowired
  private WordProperties properties;
  private Map<String, Resource> templates = new HashMap<>();

  @PostConstruct
  protected void init() {
    String resourcePath = properties.getTemplateDir() + "/**.docx";
    for (Resource resource : ResourceUtils.getResourcesByWildcard(resourcePath)) {
      String templateName = StringUtils.substringBefore(resource.getFilename(), ".docx");
      templates.put(templateName, resource);
      log.info("加载Word模版[{}]。", templateName);
    }
  }

  /**
   * 创建一个Word组件。
   * 
   * @param templateName 模版名称
   * @param model 数据模型
   * @return 返回一个Word组件。
   */
  public Word create(String templateName, Object model) {
    return create(templateName, Configure.createDefault(), model);
  }

  /**
   * 创建一个Word组件。
   * 
   * @param templateName 模版名称
   * @param config 模版配置
   * @param model 数据模型
   * @return 返回一个Word组件。
   */
  public Word create(String templateName, Configure config, Object model) {
    try (InputStream in = templates.get(templateName).getInputStream()) {
      XWPFTemplate template = XWPFTemplate.compile(in, config);
      template.render(model);
      return new Word(template.getXWPFDocument());
    } catch (Exception e) {
      throw new SysException("创建Word组件时发生异常。", e);
    }
  }
}
