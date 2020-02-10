package com.cntest.su.xss.jackson;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XssModuleTest {

  @Test
  public void test() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new XssModule());
    TestBean bean = new TestBean();
    bean.setMsg("'test'");
    log.debug("{}", mapper.writeValueAsString(bean));
    bean = mapper.readValue("{\"msg\":\"'test'\"}", TestBean.class);
    log.debug(bean.getMsg());
    log.debug("{}", mapper.writeValueAsString(bean));
  }
}
