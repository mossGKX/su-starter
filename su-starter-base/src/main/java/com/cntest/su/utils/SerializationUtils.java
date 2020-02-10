package com.cntest.su.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.cntest.su.exception.SysException;

/**
 * 序列化工具类。
 */
public class SerializationUtils {
  /**
   * 深度克隆对象。
   * 
   * @param source 源对象
   * @return 返回深度克隆对象。
   */
  public static <T extends Serializable> T clone(T source) {
    if (source == null) {
      return null;
    }
    byte[] objectData = serialize(source);
    ByteArrayInputStream bais = new ByteArrayInputStream(objectData);

    try (ObjectInputStream in = new ObjectInputStream(bais)) {
      @SuppressWarnings("unchecked")
      T readObject = (T) in.readObject();
      return readObject;
    } catch (ClassNotFoundException | IOException e) {
      throw new SysException("深度克隆时发生异常。", e);
    }
  }

  /**
   * 序列化对象到指定的输出流。
   * 
   * @param obj 待序列化的对象
   * @param outputStream 输出流
   */
  public static void serialize(Serializable obj, OutputStream outputStream) {
    try (ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
      out.writeObject(obj);
    } catch (IOException e) {
      throw new SysException("序列化时时发生异常。", e);
    }
  }

  /**
   * 序列化对象成字节数组。
   * 
   * @param obj 待序列化的对象
   * @return 返回序列化后的字节数组。
   */
  public static byte[] serialize(Serializable obj) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
    serialize(obj, baos);
    return baos.toByteArray();
  }

  /**
   * 从指定的输入流反序列化对象。
   * 
   * @param inputStream 输入流
   * @return 返回反序列化对象。
   */
  public static <T> T deserialize(InputStream inputStream) {
    try (ObjectInputStream in = new ObjectInputStream(inputStream)) {
      @SuppressWarnings("unchecked")
      T obj = (T) in.readObject();
      return obj;
    } catch (ClassNotFoundException | IOException e) {
      throw new SysException("反序列化时时发生异常。", e);
    }
  }

  /**
   * 从指定的字节数组反序列化对象。
   * 
   * @param objectData 字节数组
   * @return 返回反序列化对象。
   */
  public static <T> T deserialize(byte[] objectData) {
    return SerializationUtils.deserialize(new ByteArrayInputStream(objectData));
  }

  /**
   * 私有构造函数。
   */
  private SerializationUtils() {}
}
