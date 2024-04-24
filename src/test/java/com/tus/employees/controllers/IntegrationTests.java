package com.tus.employees.controllers;

import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tus.employees.controllers.EmployeeController;
import com.tus.employees.model.Employee;

@SpringBootTest
public class IntegrationTests {

  @Autowired
  EmployeeController employeeController;

  @Test
  public void testCreateReadDelete() {
    Employee employee = new Employee("Lokesh", "Gupta");

    Employee employeeResult = employeeController.create(employee);

    Iterable<Employee> employees = employeeController.read();
    Assertions.assertThat(employees).first().hasFieldOrPropertyWithValue("firstName", "Lokesh");

    employeeController.delete(employeeResult.getId());
    Assertions.assertThat(employeeController.read()).isEmpty();
  }
  
  @Test
  public void testCreateReadUpdateDelete() {
    Employee employee = new Employee("Lokesh", "Gupta");

    Employee employeeResult = employeeController.create(employee);

    Iterable<Employee> employees = employeeController.read();
    Assertions.assertThat(employees).first().hasFieldOrPropertyWithValue("firstName", "Lokesh");

    Employee employee1 = employeeController.update(employee);
    
    employeeController.delete(employeeResult.getId());
    Assertions.assertThat(employeeController.read()).isEmpty();
  }

  @Test
  public void errorHandlingValidationExceptionThrown() {

    Assertions.assertThatExceptionOfType(ValidationException.class)
        .isThrownBy(() -> employeeController.somethingIsWrong());
  }
}
