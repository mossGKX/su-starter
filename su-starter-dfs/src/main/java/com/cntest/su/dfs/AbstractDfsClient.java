package com.cntest.su.dfs;

import com.cntest.su.utils.FileUtils;

public abstract class AbstractDfsClient implements DfsClient {
  protected String genUuidFileName(String fileName) {
    return FileUtils.getUuidFileName(fileName).replaceAll("-", "");
  }
}
