package com.mastery.java.task.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.exceptions.EmployeeNotFoundException;
import com.mastery.java.task.service.DefaultEmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @MockBean
    private DefaultEmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String testListResult;
    private static String testResult;

    @BeforeAll
    public static void initTestData(){

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resourceList = resourceLoader.getResource("classpath:testData/testListDataResults.json");
        Resource resource = resourceLoader.getResource("classpath:testData/testDataResults.json");

        try (Reader readerList = new InputStreamReader(resourceList.getInputStream(), StandardCharsets.UTF_8);
                Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {

            testListResult = FileCopyUtils.copyToString(readerList);
            testResult = FileCopyUtils.copyToString(reader);

        } catch (IOException e) {

            testListResult = "";
            testResult = "";
        }
    }

    @Test
    public void testGetEmployeeList() throws Exception {

        List<Employee> employees = Arrays.asList(
                new Employee(7L, "John", "Jonson", 0L,
                        "developer", Gender.MALE, LocalDate.of(1980, 9, 21)),
                new Employee(8L, "Monica", "Geller", 0L,
                        "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11)));

        doReturn(employees).when(employeeService).findAll();

        mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testListResult));
    }

    @Test
    public void testGetEmployee() throws Exception {

        Employee employee = new Employee(1L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(employee).when(employeeService).findById(1L);

        mockMvc.perform(get("/employees/{id}", 1L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testResult))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetEmployeeNotFound() throws Exception {

        doReturn(null).when(employeeService).findById(1L);

        mockMvc.perform(get("/rest/widget/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddEmployee() throws Exception {

        Employee employee = new Employee(1L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(employee).when(employeeService).save(employee);

        mockMvc.perform(post("/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testResult));

    }

    @Test
    public void testUpdateEmployee() throws Exception {

        Employee employeeToSave = new Employee(1L, "Monica", "Geller", 0L,
                "developer", Gender.FEMALE, LocalDate.of(1985, 2, 11));

        doReturn(employeeToSave).when(employeeService).update(employeeToSave, 1L);

        mockMvc.perform(put("/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToSave)))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(testResult))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {

        Employee employeeToSave = new Employee(1L, "Monica", "Geller", 0L,
                "HR", Gender.FEMALE, LocalDate.of(1988, 2, 11));

        doThrow(EmployeeNotFoundException.class).when(employeeService).update(employeeToSave, 8L);

        mockMvc.perform(put("/employees/{id}", 8L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToSave)))

                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEmployee() throws Exception {

        Employee employee = new Employee(1L, "Monica", "Geller", 0L,
                "HR", Gender.FEMALE, LocalDate.of(1988, 2, 11));

        doNothing().when(employeeService).deleteById(8L);

        mockMvc.perform(delete("/employees/{id}", 8L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))

                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteEmployeeNotFound() throws Exception {

        Employee employee = new Employee(1L, "Monica", "Geller", 0L,
                "HR", Gender.FEMALE, LocalDate.of(1988, 2, 11));

        doNothing().when(employeeService).deleteById(8L);

        mockMvc.perform(delete("/employee/{id}", 8L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))

                .andExpect(status().isNotFound());

    }
}
