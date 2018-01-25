package com.example.service.impl;

import com.example.exception.ConstraintsViolationException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import com.example.service.api.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the service for managing companies.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Company find(Long companyId) throws EntityNotFoundException {
        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + companyId);
        }
        return company;
    }

    @Override
    @Transactional
    public Company save(Company company)  {
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public Company update(Long companyId, Company company) throws EntityNotFoundException, IdMismatchingException {

        if (companyId != company.getId()) {
            throw new IdMismatchingException("Ids in URL and payload are not equal");
        }

        Company targetCompany = companyRepository.findOne(companyId);

        if (targetCompany == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + companyId);
        }
        return save(company);
    }

    @Override
    @Transactional
    public void delete(Long companyId) throws EntityNotFoundException {
        Company company = find(companyId);
        companyRepository.delete(company);
    }

    @Override
    public List<Company> list() {
        return (List<Company>) companyRepository.findAll();
    }

    @Override
    public BigDecimal getAverageSalary(Long companyId) throws EntityNotFoundException {
        Company company = find(companyId);
        return employeeRepository.getAverageSalaryForCompany(company);
    }

}