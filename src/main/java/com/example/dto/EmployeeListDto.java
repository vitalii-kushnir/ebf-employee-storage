package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO with a short information about employee.
 */
@Setter
@Getter
public class EmployeeListDto {

    private Long id;

    private String name;

    private String surname;

}
