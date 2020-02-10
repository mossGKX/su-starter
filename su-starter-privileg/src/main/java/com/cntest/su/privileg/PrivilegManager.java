package com.cntest.su.privileg;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.cntest.su.utils.ResourceUtils;
import com.cntest.su.utils.StringUtils;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.Getter;

/**
 * 权限配置管理组件。
 */
public class PrivilegManager {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private static final String PRIVILEGS_DIR = "classpath*:/META-INF/su/";
  private static final String PRIVILEGS_PATH = PRIVILEGS_DIR + "*privilegs.xml";
  @Getter
  private Privilegs privilegs = new Privilegs();

  /**
   * 根据权限编码列表生成权限配置。（用于分级授权）
   * 
   * @param codes 权限编码列表
   * @return 返回生成的权限配置。
   */
  public Privilegs genPrivilegsByCodes(List<String> codes) {
    return privilegs.gen(codes);
  }

  /**
   * 初始化加载权限配置文件。
   * 
   * @throws IOException 加载权限配置文件失败时抛出该异常。
   */
  @PostConstruct
  protected void init() throws IOException {
    Resource[] resources = ResourceUtils.getResourcesByWildcard(PRIVILEGS_PATH);
    XmlMapper xmlMapper = new XmlMapper();
    for (Resource resource : resources) {
      Privilegs tmpPrivilegs = xmlMapper.readValue(resource.getInputStream(), Privilegs.class);
      privilegs.getModules().addAll(tmpPrivilegs.getModules());
      String uri = resource.getURI().toString();
      String fileName = StringUtils.substringAfterLast(uri, "/");
      log.info("加载权限配置文件[{}]成功。", fileName);
    }
  }
}
