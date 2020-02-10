package com.cntest.su.auth.config;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 认证配置属性。
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "su.auth")
public class AuthProperties {
  /** 加密算法 */
  private String algorithmName = Sha256Hash.ALGORITHM_NAME;
  /** 密码是否用HEX编码，默认是BASE64编码 */
  private Boolean hexEncode = false;
  /** 是否加盐值 */
  private Boolean salted = true;
  /** 盐值 */
  private String salt = Sha256Hash.ALGORITHM_NAME;
}
