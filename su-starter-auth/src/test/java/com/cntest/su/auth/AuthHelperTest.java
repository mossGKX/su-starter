package com.cntest.su.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cntest.su.auth.config.AuthAutoConfiguration;
import com.cntest.su.config.BaseAutoConfiguration;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {BaseAutoConfiguration.class, AuthAutoConfiguration.class})
public class AuthHelperTest {
  @Autowired
  private AuthHelper authHelper;

  @Test
  public void test() {
    log.debug(authHelper.encodePassword("666666"));
  }
}
