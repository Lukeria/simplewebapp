package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface DefaultEmployeeService {

    Employee save(Employee employee);

    Employee update(Employee employee, Long id);

    void deleteById(Long id);

    List<Employee> findAll();

    Employee findById(Long id);
}
