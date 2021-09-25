package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDao implements DefaultEmployeeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String UPDATE_SQL = "update employee set first_name = :first_name, last_name = :last_name," +
            "department_id = :department_id, job_title = :job_title, gender = :gender, " +
            "date_of_birth = :date_of_birth where employee_id = :employee_id";

    private final String DELETE_SQL = "delete from employee where employee_id = :employee_id";

    private final String SELECT_ALL = "select * from employee";

    private final String SELECT_BY_ID_SQL = "select * from employee where employee_id = :employee_id";

    @Override
    public Employee save(Employee employee) {

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("employee_id", employee.getEmployeeId())
                .addValue("first_name", employee.getFirstName())
                .addValue("last_name", employee.getLastName())
                .addValue("department_id", employee.getDepartment())
                .addValue("job_title", employee.getJobTitle())
                .addValue("gender", employee.getGender() == null ? "" : employee.getGender().toString())
                .addValue("date_of_birth", employee.getDateOfBirth());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("employee").usingGeneratedKeyColumns("employee_id");
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        employee.setEmployeeId(id.longValue());

        return employee;
    }

    @Override
    public Employee update(Employee employee) {

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("employee_id", employee.getEmployeeId())
                .addValue("first_name", employee.getFirstName())
                .addValue("last_name", employee.getLastName())
                .addValue("department_id", employee.getDepartment())
                .addValue("job_title", employee.getJobTitle())
                .addValue("gender", employee.getGender() == null ? "" : employee.getGender().toString())
                .addValue("date_of_birth", employee.getDateOfBirth());

        namedParameterJdbcTemplate.update(UPDATE_SQL, parameters);

        return employee;
    }

    @Override
    public int deleteById(Long id) {

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("employee_id", id);

        return namedParameterJdbcTemplate.update(DELETE_SQL, parameters);
    }

    @Override
    public List<Employee> findAll() {

        return namedParameterJdbcTemplate.query(SELECT_ALL, new EmployeeMapper());
    }

    @Override
    public Optional<Employee> findById(Long id) {

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("employee_id", id);

        List<Employee> employees = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL,
                parameters,
                new EmployeeMapper());

        return  employees.stream().findFirst();
    }
}
