package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeNotFoundException;
import com.mastery.java.task.service.DefaultEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private DefaultEmployeeService employeeService;

    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {

        response.sendRedirect("/employee/list");
    }

    @GetMapping("/list")
    public List<Employee> getEmployeeList() {

        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {

        return employeeService.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {

        return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {

        return employeeService.findById(id)
                .map(employeeToUpdate -> {
                    employeeToUpdate.setFirstName(employee.getFirstName());
                    employeeToUpdate.setLastName(employee.getLastName());
                    employeeToUpdate.setDepartment(employee.getDepartment());
                    employeeToUpdate.setJobTitle(employee.getJobTitle());
                    employeeToUpdate.setGender(employee.getGender());
                    employeeToUpdate.setDateOfBirth(employee.getDateOfBirth());
                    return employeeService.update(employeeToUpdate);
                })
                .orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {

        if(employeeService.deleteById(id) == 0){
            throw new EmployeeNotFoundException(id);
        };

    }

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String employeeNotFoundHandler(EmployeeNotFoundException exception) {

        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception exception) {

        return exception.getMessage();
    }

}
