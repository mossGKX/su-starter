package com.cntest.su.fastdfs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import com.cntest.su.fastdfs.FastFileClient;
import com.github.tobato.fastdfs.FdfsClientConfig;

@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastDFSAutoConfiguration {
  /**
   * 配置FastDFS客户端组件。
   * 
   * @return 返回FastDFS客户端组件。
   */
  @Bean
  public FastFileClient fastFileClient() {
    return new FastFileClient();
  }
}
