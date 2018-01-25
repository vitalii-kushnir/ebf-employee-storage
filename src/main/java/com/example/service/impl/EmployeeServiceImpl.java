package com.example.service.impl;

import com.example.exception.ConstraintsViolationException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.model.Employee;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import com.example.service.api.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private CompanyRepository companyRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Employee find(Long employeeId) throws EntityNotFoundException {
        Employee employee = employeeRepository.findOne(employeeId);
        if (employee == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + employeeId);
        }
        return employee;
    }

    @Override
    public List<Employee> findEmployeesOfCompany(Long companyId) throws EntityNotFoundException {
        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + companyId);
        }
        return employeeRepository.findEmployeesByCompany(company);
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee update(Long employeeId, Employee employee)
            throws EntityNotFoundException, IdMismatchingException {

        if (employeeId != employee.getId()) {
            throw new IdMismatchingException("Ids in URL and payload are not equal");
        }

        Employee targetEmployee = employeeRepository.findOne(employeeId);
        if (targetEmployee == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + employeeId);
        }
        return save(employee);
    }

    @Override
    @Transactional
    public void delete(Long employeeId) throws EntityNotFoundException {
        Employee employee = find(employeeId);
        employeeRepository.delete(employee);
    }
}
