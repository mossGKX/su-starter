package com.cntest.su.message;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cntest.su.config.BaseAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {BaseAutoConfiguration.class})
public class MessageSourceTest {
  @Autowired
  private MessageSource messageSource;

  @Test
  public void testGetFromXml() {
    String moduleName = messageSource.get("xml.module.name");
    Assert.assertEquals("su-starter-base", moduleName);
  }

  @Test
  public void testGetFromYml() {
    String moduleName = messageSource.get("yml.module.name");
    Assert.assertEquals("su-starter-base", moduleName);
  }
}
