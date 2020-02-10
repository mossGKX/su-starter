package com.cntest.su.id;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cntest.su.id.config.IdAutoConfiguration;

import io.shardingsphere.core.keygen.KeyGenerator;
import lombok.extern.slf4j.Slf4j;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {IdAutoConfiguration.class})
@Slf4j
public class IdGeneratorFactoryBeanTest {
  @Autowired
  private KeyGenerator keyGenerator;

  @Test
  public void test() {
    log.debug("{}", keyGenerator.generateKey());
  }

}
