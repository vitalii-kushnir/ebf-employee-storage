package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * DTO for updating of an employee.
 */
@Setter
@Getter
public class EmployeeUpdateDto extends EmployeeCreateDto{

    @NotNull(message = "name can not be null!")
    private Long id;

}
