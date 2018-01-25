package com.example.service.api;

import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for managing companies.
 */
public interface CompanyService {

    /**
     * Method retrieves a company by the given id.
     *
     * @param companyId id of a company
     * @return company domain object
     * @throws EntityNotFoundException if company does not exist
     */
    Company find(Long companyId) throws EntityNotFoundException;

    /**
     * Method saves a company.
     *
     * @param company a company object which should be saved
     * @return company domain object
     */
    Company save(Company company);

    /**
     * Method updates information about company.
     *
     * @param companyId company id
     * @param company   employee domain object
     * @return employee domain object
     * @throws EntityNotFoundException if a company does not exist
     * @throws IdMismatchingException  if Ids in URL and payload are not equal
     */
    Company update(Long companyId, Company company)
            throws EntityNotFoundException, IdMismatchingException;

    /**
     * Method deletes a company by the given id.
     *
     * @param companyId id of an employee
     * @throws EntityNotFoundException if company does not exist
     */
    void delete(Long companyId) throws EntityNotFoundException;

    /**
     * Method retrieves a list of all companies.
     *
     * @return a list of all companies
     */
    List<Company> list();

    BigDecimal getAverageSalary(Long companyId) throws EntityNotFoundException;
}
