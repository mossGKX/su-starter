package com.cntest.su.privileg;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cntest.su.privileg.config.PrivilegAutoConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {PrivilegAutoConfiguration.class})
public class PrivilegManagerTest {
  @Autowired
  private PrivilegManager privilegManager;

  @Test
  public void test() {
    Privilegs privilegs = privilegManager.getPrivilegs();
    Assert.assertEquals(1, privilegs.getModules().size());
    Assert.assertEquals(2, privilegs.getModules().get(0).getResources().size());
    Assert.assertEquals(4,
        privilegs.getModules().get(0).getResources().get(0).getOperates().size());
  }

  @Test
  public void testGen() {
    List<String> codes = new ArrayList<>();
    codes.add("organ:view");
    codes.add("organ:add");
    Privilegs privilegs = privilegManager.genPrivilegsByCodes(codes);
    Assert.assertEquals(1, privilegs.getModules().size());
    Assert.assertEquals(1, privilegs.getModules().get(0).getResources().size());
    Assert.assertEquals(2,
        privilegs.getModules().get(0).getResources().get(0).getOperates().size());
  }
}
