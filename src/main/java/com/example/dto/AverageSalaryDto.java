package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO for the average salary.
 */
@Getter
@Setter
@NoArgsConstructor
public class AverageSalaryDto {
    private BigDecimal averageSalary;

    public AverageSalaryDto(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }
}
