package com.cntest.su.privileg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作。
 */
@Data
public class Operate {
  @JacksonXmlProperty(isAttribute = true)
  @ApiModelProperty(value = "编码")
  private String code;
  @JacksonXmlProperty(isAttribute = true)
  @ApiModelProperty(value = "名称")
  private String name;
}
