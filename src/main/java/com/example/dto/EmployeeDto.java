package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO for the information about existing company.
 */
@Setter
@Getter
public class EmployeeDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private BigDecimal salary;

    private Long companyId;

    private String companyName;

    private String address;

}
