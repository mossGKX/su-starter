package com.cntest.su.message;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import com.cntest.su.constant.Encoding;
import com.cntest.su.exception.BizException;
import com.cntest.su.utils.ResourceUtils;
import com.cntest.su.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 信息配置组件。<br>
 * 信息配置文件指定放置在/META-INF/su目录下，名称以messages结尾，支持yml文件和Properties XML文件。<br>
 */
@Slf4j
public class MessageSource extends ReloadableResourceBundleMessageSource {
  private static final String MESSAGE_DIR = "classpath*:/META-INF/su/";
  private static final String MESSAGE_PATH_PREFIX = MESSAGE_DIR + "*messages";
  private static final String XML_MESSAGE_PATH = MESSAGE_PATH_PREFIX + ".xml";
  private static final String YML_MESSAGE_PATH = MESSAGE_PATH_PREFIX + ".yml";

  /**
   * 构造方法。
   */
  public MessageSource() {
    setDefaultEncoding(Encoding.UTF8);
    setUseCodeAsDefaultMessage(true);
    loadXmlProperties();
    loadYmlProperties();
  }

  /**
   * 获取指定编码的信息。
   * 
   * @param code 信息编码
   * @param vars 信息变量
   * @return 返回指定编码的信息。
   */
  public String get(String code, Object... vars) {
    return getMessage(code, vars, null);
  }

  /**
   * 抛出业务异常。
   * 
   * @param code 信息编码
   * @param vars 信息变量
   */
  public void thrown(String code, Object... vars) {
    throw new BizException(code, get(code, vars));
  }

  /**
   * 加载YML配置文件。
   */
  private void loadYmlProperties() {
    YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
    Resource[] resources = ResourceUtils.getResourcesByWildcard(YML_MESSAGE_PATH);
    yaml.setResources(resources);
    setCommonMessages(yaml.getObject());
    for (Resource resource : resources) {
      String fileName = resource.getFilename();
      log.info("加载配置信息文件[{}]成功。", fileName);
    }
  }

  /**
   * 加载XML配置文件。
   */
  private void loadXmlProperties() {
    Resource[] resources = ResourceUtils.getResourcesByWildcard(XML_MESSAGE_PATH);
    for (Resource resource : resources) {
      String fileName = resource.getFilename();
      String basename =
          MESSAGE_DIR.replace("*", "") + StringUtils.substringBeforeLast(fileName, ".");
      addBasenames(basename);
      log.info("加载配置信息文件[{}]成功。", fileName);
    }
  }
}
