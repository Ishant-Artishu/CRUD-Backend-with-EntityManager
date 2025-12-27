package com.IshantProject.demo.DAO;

import com.IshantProject.demo.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAll();

    Employee save(Employee theEmployee);

    Employee findById(int EmployeeId);

    void deleteById(int EmployeeId);
}
