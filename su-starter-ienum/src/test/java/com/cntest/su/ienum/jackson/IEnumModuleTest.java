package com.cntest.su.ienum.jackson;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cntest.su.config.BaseAutoConfiguration;
import com.cntest.su.ienum.config.IEnumAutoConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableWebMvc
@ContextConfiguration(classes = {BaseAutoConfiguration.class, JacksonAutoConfiguration.class,
    IEnumAutoConfiguration.class})
public class IEnumModuleTest {
  @Autowired
  private ObjectMapper mapper;

  @Test
  public void test() throws Exception {
    TestBean bean = new TestBean();
    String json = mapper.writeValueAsString(bean);
    Assert.assertEquals("{\"num\":\"1\"}", json);
  }
}
