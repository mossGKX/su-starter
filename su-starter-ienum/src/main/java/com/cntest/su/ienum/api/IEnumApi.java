package com.cntest.su.ienum.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cntest.su.ienum.vo.IEnumVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/enums", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "枚举")
public class IEnumApi {
  @Autowired
  private IEnumManager ienumManager;

  @ApiOperation(value = "获取枚举列表")
  @GetMapping
  public List<IEnumVo> list() {
    return ienumManager.getIenums();
  }
}
