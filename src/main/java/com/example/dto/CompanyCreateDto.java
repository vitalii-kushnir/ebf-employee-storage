package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * DTO fro the creation of a new company.
 */
@Setter
@Getter
public class CompanyCreateDto {

    @NotNull(message = "Company name can not be null!")
    private String name;
}
