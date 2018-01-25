package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO for creation of a new employee.
 */
@Setter
@Getter
public class EmployeeCreateDto {

    @NotNull(message = "name can not be null!")
    private String name;

    @NotNull(message = "Surname can not be null!")
    private String surname;

    @NotNull(message = "Email can not be null!")
    private String email;

    @NotNull(message = "Salary can not be null!")
    private BigDecimal salary;

    @NotNull(message = "Company id can not be null!")
    private Long companyId;

    @NotNull(message = "Address can not be null!")
    private String address;

}
