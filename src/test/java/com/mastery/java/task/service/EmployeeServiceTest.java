package com.mastery.java.task.service;

import com.mastery.java.task.dao.DefaultEmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private DefaultEmployeeService employeeService;

    @MockBean
    private DefaultEmployeeDao employeeDao;

    @Test
    public void testFindAll() {

        List<Employee> employees = Arrays.asList(
                new Employee(7L, "John", "Jonson", 0L,
                        "developer", Gender.MALE, LocalDate.of(1980, 9, 21)),
                new Employee(8L, "Monica", "Geller", 0L,
                        "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11)));

        doReturn(employees).when(employeeDao).findAll();

        List<Employee> employeesResult = employeeService.findAll();

        Assertions.assertEquals(2, employeesResult.size());
        Assertions.assertSame(employees.get(0), employeesResult.get(0));
        Assertions.assertSame(employees.get(1), employeesResult.get(1));
        Assertions.assertNotNull(employeesResult);
    }

    @Test
    public void testFindById() {

        Employee employee = new Employee(8L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(Optional.of(employee)).when(employeeDao).findById(8L);

        Employee employeeResult = employeeService.findById(8L);

        Assertions.assertEquals(employee, employeeResult);
        Assertions.assertNotNull(employeeResult);

    }

    @Test
    public void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(employeeDao).findById(10L);

        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.findById(8L);
        });

    }

    @Test
    public void testSave() {

        Employee employee = new Employee(8L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(employee).when(employeeDao).save(employee);

        Employee employeeResult = employeeService.save(employee);

        Assertions.assertEquals(employee, employeeResult);
        Assertions.assertNotNull(employeeResult);

    }

    @Test
    public void testUpdate() {

        Employee employee = new Employee(8L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(Optional.of(employee)).when(employeeDao).findById(8L);
        doReturn(employee).when(employeeDao).update(employee);

        Employee employeeResult = employeeService.update(employee, 8L);

        Assertions.assertEquals(employee, employeeResult);
        Assertions.assertNotNull(employeeResult);

    }

    @Test
    public void testDelete() {

        doReturn(8).when(employeeDao).deleteById(8L);

        Assertions.assertDoesNotThrow(() -> {
            employeeService.deleteById(8L);
        });
    }
}
