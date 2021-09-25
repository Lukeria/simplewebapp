package com.mastery.java.task.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {

        super("Couldn't find employee with id " + id);
    }
}
