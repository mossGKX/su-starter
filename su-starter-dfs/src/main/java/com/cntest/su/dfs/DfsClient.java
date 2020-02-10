package com.cntest.su.dfs;

import java.io.File;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 分布式文件服务客户端接口。
 */
public interface DfsClient {
  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @return 返回上传后的文件名。
   */
  String upload(File file);

  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @param metadataMap 文件元数据
   * @return 返回上传后的文件名。
   */
  String upload(File file, Map<String, String> metadataMap);

  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @return 返回上传后的文件名。
   */
  String upload(MultipartFile file);

  /**
   * 上传文件。
   * 
   * @param file 文件对象
   * @param metadataMap 文件元数据
   * @return 返回上传后的文件名。
   */
  String upload(MultipartFile file, Map<String, String> metadataMap);

  /**
   * 下载文件。
   * 
   * @param fileName 文件名称
   * @return 返回文件字节数组。
   */
  byte[] download(String fileName);

  /**
   * 下载并转换成本地文件。
   * 
   * @param fileName 文件名称
   * @return 返回本地文件对象。
   */
  File downloadToFile(String fileName);

  /**
   * 删除文件。
   * 
   * @param fileName 文件名称
   */
  void delete(String fileName);

  /**
   * 获取文件元数据。
   * 
   * @param fileName 文件名称
   * @return 返回文件元数据。
   */
  Map<String, String> getMetadata(String fileName);
}
