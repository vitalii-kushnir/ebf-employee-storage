package com.example.controller;

import com.example.dto.CompanyCreateDto;
import com.example.dto.CompanyDto;
import com.example.exception.ConstraintsViolationException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.service.api.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for company API
 */
@Controller
public class CompanyController {

    CompanyService companyService;

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
        List<Company> companies = companyService.list();
        return makeCompanyListDto(companies);
    }

    /**
     * Method returns company with a given id.
     *
     * @param companyId company id
     * @return company DTO
     * @throws EntityNotFoundException if company does does not exist
     */
    @GetMapping("/api/company/{companyId}")
    @ResponseBody
    public CompanyDto find(@PathVariable("companyId") Long companyId) throws EntityNotFoundException {
        Company company = companyService.find(companyId);
        return makeCompanyDto(company);
    }

    /**
     * Method deletes company with the given id.
     *
     * @param companyId company id
     * @throws EntityNotFoundException if company does does not exist
     */
    @DeleteMapping("/api/company/{companyId}")
    @ResponseBody
    public void delete(@PathVariable("companyId") Long companyId) throws EntityNotFoundException {
        companyService.delete(companyId);
    }

    /**
     * Method updates company with the given id.
     *
     * @param companyId company id
     * @param dto       DTO with new company data
     * @return
     * @throws ConstraintsViolationException if new data is not valid
     * @throws EntityNotFoundException       if company does not exist
     */
    @PutMapping("/api/company/{companyId}")
    @ResponseBody
    public CompanyDto update(@PathVariable("companyId") Long companyId, @RequestBody CompanyDto dto)
            throws ConstraintsViolationException, EntityNotFoundException, IdMismatchingException {
        Company company = companyService.update(companyId, makeCompany((dto)));
        return makeCompanyDto(company);
    }

    /**
     * Method creates a new company.
     *
     * @param dto new company DTO
     * @return company DTO
     * @throws ConstraintsViolationException if new data is not valid
     */
    @PostMapping("/api/company")
    @ResponseBody
    public CompanyDto create(@RequestBody CompanyCreateDto dto) throws ConstraintsViolationException {
        Company company = companyService.save(makeCompany((dto)));
        return makeCompanyDto(company);
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
