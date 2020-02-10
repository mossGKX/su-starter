package com.cntest.su.dfs.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.cntest.su.dfs.AbstractDfsClient;
import com.cntest.su.exception.SysException;
import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

/**
 * FastDFS客户端组件。
 */
public class FastDfsClient extends AbstractDfsClient {
  @Autowired
  private FastFileStorageClient storageClient;

  @Override
  public String upload(File file) {
    return upload(file, new HashMap<String, String>());
  }

  @Override
  public String upload(File file, Map<String, String> metadata) {
    try (InputStream in = new FileInputStream(file)) {
      StorePath storePath = storageClient.uploadFile(in, file.length(),
          FilenameUtils.getExtension(file.getName()), toMataData(metadata));
      return storePath.getFullPath();
    } catch (IOException e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public String upload(MultipartFile file) {
    return upload(file, new HashMap<String, String>());
  }

  @Override
  public String upload(MultipartFile file, Map<String, String> metadata) {
    try {
      StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
          FilenameUtils.getExtension(file.getOriginalFilename()), toMataData(metadata));
      return storePath.getFullPath();
    } catch (IOException e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public byte[] download(String fileName) {
    StorePath storePath = StorePath.praseFromUrl(fileName);
    return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(),
        new DownloadByteArray());
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
    StorePath storePath = StorePath.praseFromUrl(fileName);
    storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
  }

  @Override
  public Map<String, String> getMetadata(String fileName) {
    StorePath storePath = StorePath.praseFromUrl(fileName);
    return toMetadata(storageClient.getMetadata(storePath.getGroup(), storePath.getPath()));
  }

  /**
   * 将Map元数据转换成MataData集合。
   * 
   * @param metadata Map元数据
   * @return 返回MataData集合。
   */
  private Set<MataData> toMataData(Map<String, String> metadata) {
    Set<MataData> matadata = new HashSet<>();
    for (Entry<String, String> data : metadata.entrySet()) {
      matadata.add(new MataData(data.getKey(), data.getValue()));
    }
    return matadata;
  }

  /**
   * 将MataData集合转换成Map元数据。
   * 
   * @param matadata MataData集合
   * @return 返回Map元数据。
   */
  private Map<String, String> toMetadata(Set<MataData> matadata) {
    Map<String, String> metadata = new HashMap<>();
    for (MataData data : matadata) {
      metadata.put(data.getName(), data.getValue());
    }
    return metadata;
  }
}
