package com.cntest.su.excel.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 部门。
 */
@Data
public class Department {
  /** 名称 */
  private String name;
  /** 主管 */
  private Employee chief;
  /** 关联职员列表 */
  private List<Employee> employees = new ArrayList<>();

  public static Department single() {
    return multi(1).get(0);
  }

  public static List<Department> multi(Integer count) {
    List<Department> departments = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      Department department = new Department();
      department.setName("部门" + i);

      Employee chief = Employee.single();
      chief.setName("部门主管" + i);

      department.setChief(chief);
      department.setEmployees(Employee.multi(10));

      departments.add(department);
    }

    return departments;
  }
}
