package com.example.service.api;

import com.example.exception.ConstraintsViolationException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;

import java.util.List;

public interface CompanyService {

    Company find(Long companyId) throws EntityNotFoundException;

    Company save(Company comnany) throws ConstraintsViolationException;

    Company update(Long companyId, Company comnany)
            throws ConstraintsViolationException, EntityNotFoundException, IdMismatchingException;

    void delete(Long companyId) throws EntityNotFoundException;

    List<Company> list();
}
