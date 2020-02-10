package com.cntest.su.jpa.entity;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.cntest.su.utils.SpringUtils;

import io.shardingsphere.core.keygen.KeyGenerator;

public class IdGenerator implements IdentifierGenerator {
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) {
    return getKeyGenerator().generateKey();
  }

  private KeyGenerator getKeyGenerator() {
    return SpringUtils.getBean(KeyGenerator.class);
  }
}
