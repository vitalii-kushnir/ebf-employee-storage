package com.example.service.impl;

import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.model.Employee;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import com.example.service.api.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    private CompanyRepository companyRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Employee find(Long employeeId) throws EntityNotFoundException {
        LOGGER.info("Searching a employee by id={}", employeeId);
        Employee employee = employeeRepository.findOne(employeeId);
        if (employee == null) {
            LOGGER.error("Could not find employee with id={}", employeeId);
            throw new EntityNotFoundException("Could not find entity with id: " + employeeId);
        }
        return employee;
    }

    @Override
    public List<Employee> findEmployeesOfCompany(Long companyId) throws EntityNotFoundException {
        LOGGER.info("Searching employees by company id={}", companyId);
        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            LOGGER.error("Could not find company with id={}" + companyId);
            throw new EntityNotFoundException("Could not find company with id: " + companyId);
        }
        return employeeRepository.findEmployeesByCompany(company);
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        LOGGER.info("Saving employee");
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee update(Long employeeId, Employee employee)
            throws EntityNotFoundException, IdMismatchingException {
        LOGGER.info("Updating an employee with id={}", employeeId);
        if (employeeId != employee.getId()) {
            LOGGER.error("Ids in URL and payload are not equal {} != {}", employeeId, employee.getId());
            throw new IdMismatchingException("Ids in URL and payload are not equal");
        }

        Employee targetEmployee = employeeRepository.findOne(employeeId);
        if (targetEmployee == null) {
            LOGGER.error("Could not find employee with id={}" + employeeId);
            throw new EntityNotFoundException("Could not find entity with id: " + employeeId);
        }
        return save(employee);
    }

    @Override
    @Transactional
    public void delete(Long employeeId) throws EntityNotFoundException {
        LOGGER.info("Removing an employee with id={}", employeeId);
        Employee employee = find(employeeId);
        employeeRepository.delete(employee);
    }
}
