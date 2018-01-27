package com.example.controller;

import com.example.ApiError;
import com.example.dto.AverageSalaryDto;
import com.example.dto.CompanyCreateDto;
import com.example.dto.CompanyDto;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.service.api.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for cthe ompany API.
 */
@Controller
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Method returns list of companies.
     *
     * @return list of companies
     */
    @GetMapping("/api/company")
    @ResponseBody
    public List<CompanyDto> list() {
        LOGGER.info("Retrieving a list of companies.");
        List<Company> companies = companyService.list();
        return makeCompanyListDto(companies);
    }

    /**
     * Method returns company with a given id.
     *
     * @param companyId company id
     * @return company DTO
     * @throws EntityNotFoundException if company does not exist
     */
    @GetMapping("/api/company/{companyId}")
    @ResponseBody
    public CompanyDto find(@PathVariable("companyId") Long companyId) throws EntityNotFoundException {
        LOGGER.info("Retrieving a company by id={}", companyId);
        Company company = companyService.find(companyId);
        return makeCompanyDto(company);
    }

    /**
     * Method deletes company with the given id.
     *
     * @param companyId company id
     * @throws EntityNotFoundException if company does not exist
     */
    @DeleteMapping("/api/company/{companyId}")
    @ResponseBody
    public void delete(@PathVariable("companyId") Long companyId) throws EntityNotFoundException {
        LOGGER.info("Deleting a company by id={}", companyId);
        companyService.delete(companyId);
    }

    /**
     * Method updates company with the given id.
     *
     * @param companyId company id
     * @param dto       DTO with new company data
     * @return company DTO
     * @throws EntityNotFoundException if company does not exist
     * @throws IdMismatchingException  if Ids in URL and payload are not equal
     */
    @PutMapping("/api/company/{companyId}")
    @ResponseBody
    public CompanyDto update(@PathVariable("companyId") Long companyId, @Valid @RequestBody CompanyDto dto)
            throws EntityNotFoundException, IdMismatchingException {
        LOGGER.info("Updating a company with id={}", companyId);
        Company company = companyService.update(companyId, makeCompany((dto)));
        return makeCompanyDto(company);
    }

    /**
     * Method creates a new company.
     *
     * @param dto new company DTO
     * @return company DTO
     */
    @PostMapping("/api/company")
    @ResponseBody
    public CompanyDto create(@Valid @RequestBody CompanyCreateDto dto) {
        Company company = companyService.save(makeCompany((dto)));
        return makeCompanyDto(company);
    }

    @GetMapping("/api/company/{companyId}/average-salary")
    @ResponseBody
    public AverageSalaryDto getAverageSalary(@PathVariable Long companyId) throws EntityNotFoundException {
        BigDecimal averageSalary = companyService.getAverageSalary(companyId);
        return new AverageSalaryDto(averageSalary);
    }

    /**
     * Method handles DataIntegrityViolationException.
     */
    @ExceptionHandler({ DataIntegrityViolationException.class })
    @ResponseBody
    public ResponseEntity<?> handleDataIntegrityViolationException() {
        ApiError exception = new ApiError(HttpStatus.BAD_REQUEST,
                "Cannot create a new company. Unique index or primary key violation exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    private Company makeCompany(CompanyDto dto) {
        Company company = new Company();
        company.setId(dto.getId());
        company.setName(dto.getName());
        return company;
    }

    private Company makeCompany(CompanyCreateDto dto) {
        Company company = new Company();
        company.setName(dto.getName());
        return company;
    }

    private CompanyDto makeCompanyDto(Company company) {
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        return dto;
    }

    private List<CompanyDto> makeCompanyListDto(Collection<Company> companies) {
        return companies.stream()
                .map(this::makeCompanyDto)
                .collect(Collectors.toList());
    }
}
