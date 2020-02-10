package com.cntest.su.excel.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cntest.su.utils.DateUtils;

import lombok.Data;

/**
 * 职员。
 */
@Data
public class Employee {
  /** 姓名 */
  private String name;
  /** 年龄 */
  private Integer age = 18;
  /** 生日 */
  private Date birthDate;
  /** 基本工资 */
  private Double payment = 0d;
  /** 奖金比例 */
  private Double bonus = 0d;

  public static Employee single() {
    return multi(1).get(0);
  }

  public static List<Employee> multi(Integer count) {
    List<Employee> employees = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      Employee employee = new Employee();
      employee.setName("职员" + i);
      employee.setAge(30 + i);
      employee.setBirthDate(DateUtils.parse(1983 - i + "-01-01"));
      employee.setPayment(20000.00 + 1000 * i);
      employee.setBonus(0.25 - i * 0.01);
      employees.add(employee);
    }

    return employees;
  }
}
