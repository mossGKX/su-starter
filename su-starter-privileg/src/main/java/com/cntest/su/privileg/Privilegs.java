package com.cntest.su.privileg;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限配置。
 */
@Data
@JacksonXmlRootElement(localName = "privilegs")
public class Privilegs {
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "module")
  @ApiModelProperty(value = "模块")
  private List<Module> modules = new ArrayList<>();

  /**
   * 根据权限编码生成权限配置。
   * 
   * @param codes 权限编码列表
   * @return 返回生成的权限配置。
   */
  public Privilegs gen(List<String> codes) {
    Privilegs privilegs = new Privilegs();
    for (Module module : modules) {
      Module distModule = module.gen(codes);
      if (!distModule.getResources().isEmpty()) {
        privilegs.getModules().add(distModule);
      }
    }
    return privilegs;
  }
}
