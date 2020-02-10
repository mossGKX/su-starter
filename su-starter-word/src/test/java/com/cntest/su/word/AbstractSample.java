package com.cntest.su.word;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cntest.su.word.config.WordAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WordAutoConfiguration.class)
public abstract class AbstractSample {
  protected String outputDir = "src/test/resources/META-INF/su/export";
  @Autowired
  protected WordFactory wordFactory;
}
