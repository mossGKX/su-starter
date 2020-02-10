package com.cntest.su.dfs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.cntest.su.dfs.config.DfsAutoConfiguration;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DfsAutoConfiguration.class)
@Slf4j
public abstract class AbstractDfsClientTest {
  @Autowired
  protected DfsClient dfsClient;

  @Test
  public void testUploadFile() {
    String fileName = dfsClient.upload(getFile());
    log.debug("fileName:{} ", fileName);
    dfsClient.delete(fileName);
  }

  @Test
  public void testUploadFileMapOfStringString() {
    String fileName = dfsClient.upload(getFile(), genMetadataMap());
    log.debug("fileName:{} ", fileName);
    log.debug("metadata: {}", dfsClient.getMetadata(fileName));
    dfsClient.delete(fileName);
  }

  @Test
  public void testUploadMultipartFile() {
    String fileName = dfsClient.upload(genMultipartFile());
    log.debug("fileName:{} ", fileName);
    dfsClient.delete(fileName);
  }

  @Test
  public void testUploadMultipartFileMapOfStringString() {
    String fileName = dfsClient.upload(genMultipartFile(), genMetadataMap());
    log.debug("fileName:{} ", fileName);
    log.debug("metadata: {}", dfsClient.getMetadata(fileName));
    dfsClient.delete(fileName);
  }

  @Test
  public void testDownload() {
    String fileName = dfsClient.upload(genMultipartFile());
    log.debug("fileName:{} ", fileName);
    dfsClient.download(fileName);
    dfsClient.delete(fileName);
  }

  @Test
  public void testDownloadToFile() {
    String fileName = dfsClient.upload(getFile());
    log.debug("fileName:{} ", fileName);
    dfsClient.downloadToFile(fileName);
    dfsClient.delete(fileName);
  }

  private Map<String, String> genMetadataMap() {
    Map<String, String> metadataMap = new HashMap<>();
    metadataMap.put("author", "张三");
    metadataMap.put("translator", "李四");
    return metadataMap;
  }

  private MultipartFile genMultipartFile() {
    return new MockMultipartFile("data", "filename.txt", "text/plain", "test content".getBytes());
  }

  private File getFile() {
    return FileUtils.getFile("src/test/resources/test.png");
  }
}
