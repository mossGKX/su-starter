package com.cntest.su.privileg;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源。
 */
@Data
public class Resource {
  @JacksonXmlProperty(isAttribute = true)
  @ApiModelProperty(value = "编码")
  private String code;
  @JacksonXmlProperty(isAttribute = true)
  @ApiModelProperty(value = "名称")
  private String name;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "operate")
  @ApiModelProperty(value = "操作")
  private List<Operate> operates = new ArrayList<>();

  /**
   * 根据权限编码生成权限资源。
   * 
   * @param codes 权限编码列表
   * @return 返回生成的权限资源。
   */
  public Resource gen(List<String> codes) {
    Resource resource = new Resource();
    resource.code = code;
    resource.name = name;
    for (Operate operate : operates) {
      if (codes.contains(operate.getCode())) {
        resource.getOperates().add(operate);
      }
    }
    return resource;
  }

  public void setOperates(List<Operate> operates) {
    for (Operate operate : operates) {
      operate.setCode(getCode() + ":" + operate.getCode());
    }
    this.operates = operates;
  }
}
