package com.cntest.su.fastdfs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.cntest.su.exception.SysException;
import com.cntest.su.utils.FileUtils;
import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

/**
 * FastDFS客户端组件。
 */
public class FastFileClient {
  private static final String TEMP_FILE_PATH = System.getProperty("java.io.tmpdir") + "collect/";
  @Autowired
  private FastFileStorageClient storageClient;

  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @return 文件路径
   */
  public String uploadFile(File file) {
    return uploadFile(file, new HashMap<String, String>());
  }

  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @param metadata 文件元数据
   * @return 文件路径
   */
  public String uploadFile(File file, Map<String, String> metadata) {
    try (InputStream in = new FileInputStream(file)) {
      StorePath storePath = storageClient.uploadFile(in, file.length(),
          FilenameUtils.getExtension(file.getName()), toMataData(metadata));
      return storePath.getFullPath();
    } catch (IOException e) {
      throw new SysException("上传文件失败。", e);
    }
  }

  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @return 文件路径
   */
  public String uploadFile(MultipartFile file) {
    return uploadFile(file, new HashMap<String, String>());
  }

  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @param metadata 文件元数据
   * @return 文件路径
   */
  public String uploadFile(MultipartFile file, Map<String, String> metadata) {
    try {
      StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
          FilenameUtils.getExtension(file.getOriginalFilename()), toMataData(metadata));
      return storePath.getFullPath();
    } catch (IOException e) {
      throw new SysException("上传文件失败。", e);
    }
  }

  /**
   * 下载文件。
   * 
   * @param filePath 文件路径
   * @return 返回文件字节数组。
   */
  public byte[] downloadFile(String filePath) {
    StorePath storePath = StorePath.praseFromUrl(filePath);
    return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(),
        new DownloadByteArray());
  }

  /**
   * 下载文件。
   * 
   * @param filePath 下载的文件目录。
   * @return File。
   */
  public File getFile(String filePath) {
    byte[] bytes = downloadFile(filePath);
    String fileName = UUID.randomUUID() + FileUtils.getFileType(filePath);
    File dir = new File(TEMP_FILE_PATH);
    if (!dir.exists() && dir.isDirectory()) {
      dir.mkdirs();
    }
    File file = new File(TEMP_FILE_PATH + File.separator + fileName);
    try (FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos)) {
      bos.write(bytes);
    } catch (IOException e) {
      throw new SysException("下载文件失败。", e);
    }
    return file;

  }

  /**
   * 删除文件。
   * 
   * @param filePath 文件路径
   */
  public void deleteFile(String filePath) {
    StorePath storePath = StorePath.praseFromUrl(filePath);
    storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
  }

  /**
   * 获取文件元数据。
   * 
   * @param filePath 文件路径
   * @return 返回文件元数据。
   */
  public Map<String, String> getMetadata(String filePath) {
    StorePath storePath = StorePath.praseFromUrl(filePath);
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
