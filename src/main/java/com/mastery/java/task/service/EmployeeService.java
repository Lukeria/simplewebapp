package com.mastery.java.task.service;

import com.mastery.java.task.dao.DefaultEmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements DefaultEmployeeService{

    @Autowired
    private DefaultEmployeeDao employeeDao;

    @Override
    public Employee save(Employee employee) {

        return employeeDao.save(employee);
    }

    @Override
    public Employee update(Employee employee, Long id) {

        return employeeDao.findById(id)
                .map(employeeToUpdate -> {
                    employeeToUpdate.setFirstName(employee.getFirstName());
                    employeeToUpdate.setLastName(employee.getLastName());
                    employeeToUpdate.setDepartment(employee.getDepartment());
                    employeeToUpdate.setJobTitle(employee.getJobTitle());
                    employeeToUpdate.setGender(employee.getGender());
                    employeeToUpdate.setDateOfBirth(employee.getDateOfBirth());
                    return employeeDao.update(employeeToUpdate);
                })
                .orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {

        if(employeeDao.deleteById(id)== 0){
            throw new EmployeeNotFoundException(id);
        };
    }

    @Override
    public List<Employee> findAll() {

        return employeeDao.findAll();
    }

    @Override
    public Employee findById(Long id) {

        return employeeDao.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }
}
