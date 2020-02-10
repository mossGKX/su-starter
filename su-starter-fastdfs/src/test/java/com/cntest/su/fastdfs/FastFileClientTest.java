package com.cntest.su.fastdfs;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@Slf4j
public class FastFileClientTest {
  @Autowired
  private FastFileClient client;

  @Test
  public void testUploadFile() throws Exception {
    MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain",
        "fastdfs test content".getBytes());
    String filePath = client.uploadFile(file);
    Assert.assertNotNull(filePath);
    log.debug(filePath);
  }

  @Test
  public void testUploadFileWithMeta() throws Exception {
    MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain",
        "fastdfs test content".getBytes());
    Map<String, String> metadata = new HashMap<>();
    metadata.put("作者", "贺颂");
    String filePath = client.uploadFile(file, metadata);

    metadata = client.getMetadata(filePath);
    Assert.assertEquals(1, metadata.size());
    log.debug(filePath);
  }
}
