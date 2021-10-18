package com.mastery.java.task.exceptions;

public class EmployeeServiceNotFoundException extends RuntimeException {

    public EmployeeServiceNotFoundException(Long id) {

        super("Couldn't find employee with id " + id);
    }
}
