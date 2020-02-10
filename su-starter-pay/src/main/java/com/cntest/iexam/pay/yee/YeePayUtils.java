package com.cntest.iexam.pay.yee;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import com.cntest.su.utils.StringUtils;

/**
 * 易宝支付加解密工具类。
 */
public class YeePayUtils {
  /**
   * 签名。
   * 
   * @param strs 字符数组
   * @param key 密钥
   * @return 返回签名。
   */
  public static String sign(Collection<String> strs, String key) {
    String content = StringUtils.join(strs, "");
    return hmacMd5(content, key);
  }

  /**
   * 安全签名。
   * 
   * @param strs 字符数组
   * @param key 密钥
   * @return 返回安全签名。
   */
  public static String signSafe(Collection<String> strs, String key) {
    String content = StringUtils.join(removeEmpty(strs), "#");
    return hmacMd5(content, key);
  }

  /**
   * 验证签名。
   * 
   * @param strs 字符数组
   * @param sign 待验证签名
   * @param signSafe 安全签名
   * @param key 密钥
   * @return 如果验证通过返回true，否则返回false。
   */
  public static Boolean verify(Collection<String> strs, String sign, String signSafe, String key) {
    String localSign = sign(strs, key);
    String localSignSafe = signSafe(strs, key);
    return localSign.equals(sign) && localSignSafe.equals(signSafe);
  }

  /**
   * HMACMD5签名。
   * 
   * @param content 内容
   * @param key 密钥
   * @return 返回HMACMD5签名。
   */
  private static String hmacMd5(String content, String key) {
    return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmacHex(content);
  }

  /**
   * 从字符数组中移除内容为空的元素。
   * 
   * @param strs 字符数组
   * @return 返回已清理的字符数组。
   */
  private static Collection<String> removeEmpty(Collection<String> strs) {
    return strs.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
  }

  /**
   * 私有构造方法。
   */
  private YeePayUtils() {}
}
