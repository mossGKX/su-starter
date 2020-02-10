package com.cntest.su.dfs.obs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.cntest.su.dfs.AbstractDfsClient;
import com.cntest.su.exception.SysException;
import com.cntest.su.utils.CollectionUtils;
import com.obs.services.ObsClient;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;

public class ObsDfsClient extends AbstractDfsClient {
  @Autowired
  private ObsProperties props;
  private ObsClient obsClient;

  @PostConstruct
  public void init() {
    obsClient =
        new ObsClient(props.getAccessKeyId(), props.getAccessKeySecret(), props.getEndpoint());
  }

  @PreDestroy
  public void destroy() {
    try {
      obsClient.close();
    } catch (IOException e) {
      throw new SysException("关闭Obs客户端时发生异常。", e);
    }
  }

  @Override
  public String upload(File file) {
    return upload(file, null);
  }

  @Override
  public String upload(File file, Map<String, String> metadataMap) {
    String fileName = genUuidFileName(file.getName());
    ObjectMetadata metadata = null;
    if (CollectionUtils.isNotEmpty(metadataMap)) {
      metadata = map2metadata(metadataMap);
    }
    PutObjectResult result =
        obsClient.putObject(props.getDefaultBucketName(), fileName, file, metadata);
    return result.getObjectKey();
  }

  @Override
  public String upload(MultipartFile file) {
    return upload(file, null);
  }

  @Override
  public String upload(MultipartFile file, Map<String, String> metadataMap) {
    String fileName = genUuidFileName(file.getOriginalFilename());
    ObjectMetadata metadata = null;
    if (CollectionUtils.isNotEmpty(metadataMap)) {
      metadata = map2metadata(metadataMap);
    }
    try (InputStream in = file.getInputStream()) {
      PutObjectResult result =
          obsClient.putObject(props.getDefaultBucketName(), fileName, in, metadata);
      return result.getObjectKey();
    } catch (IOException e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public byte[] download(String fileName) {
    ObsObject obsObject = obsClient.getObject(props.getDefaultBucketName(), fileName);
    return com.cntest.su.utils.FileUtils.toByteArray(obsObject.getObjectContent());
  }

  @Override
  public File downloadToFile(String fileName) {
    try {
      byte[] bytes = download(fileName);
      String tmpFileName = FileUtils.getTempDirectoryPath() + File.separator + fileName;
      File tmpFile = FileUtils.getFile(tmpFileName);
      FileUtils.writeByteArrayToFile(tmpFile, bytes);
      return tmpFile;
    } catch (IOException e) {
      throw new SysException("下载文件时发生异常。", e);
    }
  }

  @Override
  public void delete(String fileName) {
    obsClient.deleteObject(props.getDefaultBucketName(), fileName);
  }

  @Override
  public Map<String, String> getMetadata(String fileName) {
    ObjectMetadata metadata = obsClient.getObjectMetadata(props.getDefaultBucketName(), fileName);
    return metadata2map(metadata);
  }

  /**
   * 将Map元数据转换成ObjectMetadata对象。
   * 
   * @param metadata Map元数据
   * @return 返回ObjectMetadata对象。
   */
  private ObjectMetadata map2metadata(Map<String, String> metadataMap) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadataMap.forEach(metadata::addUserMetadata);
    return metadata;
  }

  /**
   * 将ObjectMetadata对象转换成Map元数据。
   * 
   * @param metadata ObjectMetadata对象
   * @return 返回Map元数据。
   */
  private Map<String, String> metadata2map(ObjectMetadata metadata) {
    Map<String, String> metadataMap = new HashMap<>();
    metadata.getMetadata().forEach((k, v) -> metadataMap.put(k, v.toString()));
    return metadataMap;
  }
}
