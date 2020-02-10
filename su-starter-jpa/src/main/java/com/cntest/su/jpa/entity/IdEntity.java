package com.cntest.su.jpa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public abstract class IdEntity {
  @Id
  @GeneratedValue(generator = "id-generator")
  @GenericGenerator(name = "id-generator", strategy = "com.cntest.su.jpa.entity.IdGenerator")
  protected Long id;
}
