package com.cntest.su.id;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.cntest.su.exception.SysException;
import com.cntest.su.id.config.IdProperties;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.KeyGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdGeneratorFactoryBean implements FactoryBean<KeyGenerator> {
  @Autowired
  private IdProperties idProperties;
  private KeyGenerator generator;

  @PostConstruct
  public void init() {
    switch (idProperties.getType()) {
      case "host":
        DefaultKeyGenerator.setWorkerId(genWorkerIdByHost());
        break;
      case "ip":
        DefaultKeyGenerator.setWorkerId(genWorkerIdByIp());
        break;
      default:
        throw new SysException("不支持的分布式id生成器类型。");
    }
    generator = new DefaultKeyGenerator();
  }

  @Override
  public KeyGenerator getObject() throws Exception {
    return generator;
  }

  @Override
  public Class<?> getObjectType() {
    return KeyGenerator.class;
  }

  private Long genWorkerIdByHost() {
    InetAddress address = getLocalHost();
    String hostName = address.getHostName();
    try {
      Long workerId = Long.valueOf(hostName.replace(hostName.replaceAll("\\d+$", ""), ""));
      log.info("启用雪花算法ID生成机制，workerId为{}", workerId);
      return workerId;
    } catch (final NumberFormatException e) {
      throw new SysException("获取workerid失败，主机名[" + hostName + "]需以数字结尾。");
    }
  }

  private Long genWorkerIdByIp() {
    InetAddress address = getLocalHost();
    byte[] ipAddressByteArray = address.getAddress();
    long workerId = 0L;
    if (ipAddressByteArray.length == 4) {
      for (byte byteNum : ipAddressByteArray) {
        workerId += byteNum & 0xFF;
      }
    }
    if (ipAddressByteArray.length == 16) {
      for (byte byteNum : ipAddressByteArray) {
        workerId += byteNum & 0B111111;
      }
    }
    log.info("启用雪花算法ID生成机制，workerId为{}", workerId);
    return workerId;
  }

  private InetAddress getLocalHost() {
    try {
      return InetAddress.getLocalHost();
    } catch (final UnknownHostException e) {
      throw new SysException("获取主机地址失败。");
    }
  }
}
