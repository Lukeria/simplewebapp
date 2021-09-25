package com.mastery.java.task.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.DefaultEmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @MockBean
    private DefaultEmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEmployeeList() throws Exception{

        List<Employee> employees = Arrays.asList(
                new Employee(7L, "John", "Jonson", 0L,
                        "developer", Gender.MALE, LocalDate.of(1980, 9, 21)),
                new Employee(8L, "Monica", "Geller", 0L,
                        "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11)));

        doReturn(employees).when(employeeService).findAll();

        ResultActions resultActions = mockMvc.perform(get("/employee/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(employees.get(0).getFirstName())))
                .andExpect(jsonPath("$[1].firstName", is(employees.get(1).getFirstName())));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        List<Employee> employeesResult = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        Assertions.assertEquals(employees.get(0), employeesResult.get(0));
        Assertions.assertEquals(employees.get(1), employeesResult.get(1));
    }

    @Test
    public void testGetEmployee() throws Exception {

        Employee employee = new Employee(8L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(Optional.of(employee)).when(employeeService).findById(8L);

       ResultActions resultActions = mockMvc.perform(get("/employee/{id}", 8L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Employee employeeResult = objectMapper.readValue(contentAsString, Employee.class);

        Assertions.assertEquals(employee, employeeResult);
    }

    @Test
    public void testGetEmployeeNotFound() throws Exception {

        doReturn(Optional.empty()).when(employeeService).findById(1L);

        mockMvc.perform(get("/rest/widget/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddEmployee() throws Exception{

        Employee employee = new Employee(8L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(employee).when(employeeService).save(employee);

        ResultActions resultActions = mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Employee employeeResult = objectMapper.readValue(contentAsString, Employee.class);

        Assertions.assertEquals(employee, employeeResult);
    }

    @Test
    public void testUpdateEmployee() throws Exception{

        Employee employeeToFind = new Employee(8L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));
        Employee employeeToSave = new Employee(8L, "Monica", "Geller", 0L,
                "HR", Gender.FEMALE, LocalDate.of(1988, 2, 11));

        doReturn(Optional.of(employeeToFind)).when(employeeService).findById(8L);
        doReturn(employeeToSave).when(employeeService).update(employeeToSave);

        ResultActions resultActions = mockMvc.perform(put("/employee/{id}", 8L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToSave)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Employee employeeResult = objectMapper.readValue(contentAsString, Employee.class);

        Assertions.assertEquals(employeeToSave, employeeResult);
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception{

        Employee employeeToSave = new Employee(1L, "Monica", "Geller", 0L,
                "HR", Gender.FEMALE, LocalDate.of(1988, 2, 11));

        doReturn(Optional.empty()).when(employeeService).findById(1L);

        mockMvc.perform(put("/employee/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToSave)))

                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEmployee() throws Exception{

        Employee employee = new Employee(1L, "Monica", "Geller", 0L,
                "HR", Gender.FEMALE, LocalDate.of(1988, 2, 11));

        doReturn(8).when(employeeService).deleteById(8L);

        mockMvc.perform(delete("/employee/{id}", 8L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))

                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteEmployeeNotFound() throws Exception{

        Employee employee = new Employee(1L, "Monica", "Geller", 0L,
                "HR", Gender.FEMALE, LocalDate.of(1988, 2, 11));

        doReturn(0).when(employeeService).deleteById(8L);

        mockMvc.perform(delete("/employee/{id}", 8L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))

                .andExpect(status().isNotFound());

    }
}
