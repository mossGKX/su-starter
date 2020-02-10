package com.cntest.su.privileg;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模块，用于对权限配置展示的页面进行分组。
 */
@Data
public class Module {
  @JacksonXmlProperty(isAttribute = true)
  @ApiModelProperty(value = "名称")
  private String name;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "resource")
  @ApiModelProperty(value = "资源")
  private List<Resource> resources = new ArrayList<>();

  /**
   * 根据权限编码生成权限模块。
   * 
   * @param codes 权限编码列表
   * @return 返货生成的权限模块。
   */
  public Module gen(List<String> codes) {
    Module module = new Module();
    module.name = name;
    for (Resource resource : resources) {
      Resource distResource = resource.gen(codes);
      if (!distResource.getOperates().isEmpty()) {
        module.getResources().add(distResource);
      }
    }
    return module;
  }
}
