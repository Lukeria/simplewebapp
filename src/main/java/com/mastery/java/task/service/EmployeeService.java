package com.mastery.java.task.service;

import com.mastery.java.task.dao.DefaultEmployeeDao;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements DefaultEmployeeService{

    @Autowired
    private DefaultEmployeeDao employeeDao;

    @Override
    public Employee save(Employee employee) {

        return employeeDao.save(employee);
    }

    @Override
    public Employee update(Employee employee) {

        return employeeDao.update(employee);
    }

    @Override
    public int deleteById(Long id) {

        return employeeDao.deleteById(id);
    }

    @Override
    public List<Employee> findAll() {

        return employeeDao.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {

        return employeeDao.findById(id);
    }
}
