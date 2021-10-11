package com.mastery.java.task.config;

import com.mastery.java.task.dao.DefaultEmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DefaultEmployeeDao employeeDao;

    @Override
    public void run(String... args) {

        jdbcTemplate.execute("drop table if exists employee");

        jdbcTemplate.execute(
                "create table employee\n" +
                        "(\n" +
                        "    employee_id   bigserial       primary key,\n" +
                        "    first_name    varchar(100) not null,\n" +
                        "    last_name     varchar(100),\n" +
                        "    department_id integer,\n"+
                        "    job_title     varchar(150),\n" +
                        "    gender        varchar(100) not null,\n" +
                        "    date_of_birth date         not null\n" +
                        ");"
        );

        List<Employee> employees = Arrays.asList(
                new Employee(null, "Karina", "Lukashevich", 2L,
                        "Developer", Gender.FEMALE, LocalDate.of(2000, 8, 30)),
                new Employee(null, "Ivan", "Ivanov", 1L,
                        "Developer", Gender.MALE, LocalDate.of(1991, 1, 1))
        );

        employees.forEach(employee -> employeeDao.save(employee));
    }
}
