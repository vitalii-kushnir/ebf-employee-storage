package com.example.service.impl;

import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import com.example.service.api.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of the service for managing companies.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyRepository companyRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Company find(Long companyId) throws EntityNotFoundException {
        LOGGER.info("Searching a company by id={}", companyId);
        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            LOGGER.error("Could not find company with id={}", companyId);
            throw new EntityNotFoundException("Could not find company with id: " + companyId);
        }
        return company;
    }

    @Override
    @Transactional
    public Company save(Company company) {
        LOGGER.info("Saving of a new company");
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public Company update(Long companyId, Company company) throws EntityNotFoundException, IdMismatchingException {
        LOGGER.info("Updating of a company with id={}", companyId);
        if (companyId != company.getId()) {
            LOGGER.error("Ids in URL and payload are not equal {} != {}", companyId, company.getId());
            throw new IdMismatchingException("Ids in URL and payload are not equal");
        }

        Company targetCompany = companyRepository.findOne(companyId);

        if (targetCompany == null) {
            LOGGER.error("Cannot find company with id={}", companyId);
            throw new EntityNotFoundException("Could not find company with id: " + companyId);
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
        LOGGER.info("Getting of a list of companies");
        return (List<Company>) companyRepository.findAll();
    }

    @Override
    public BigDecimal getAverageSalary(Long companyId) throws EntityNotFoundException {
        LOGGER.info("Getting average salary for the company with id={}", companyId);
        Company company = find(companyId);
        return employeeRepository.getAverageSalaryForCompany(company);
    }

}
