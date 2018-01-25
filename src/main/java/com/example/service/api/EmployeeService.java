package com.example.service.api;

import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Employee;

import java.util.List;

/**
 * Service for managing employees.
 */
public interface EmployeeService {

    /**
     * Method retrieves an employee by the given id.
     *
     * @param employeeId id of an employee
     * @return employee domain object
     * @throws EntityNotFoundException if employee does not exist
     */
    Employee find(Long employeeId) throws EntityNotFoundException;

    /**
     * Method retrieves a list of employees from a company.
     *
     * @param companyId id of the company
     * @return a list of employees from a company.
     * @throws EntityNotFoundException if employee does not exist
     */
    List<Employee> findEmployeesOfCompany(Long companyId) throws EntityNotFoundException;

    /**
     * Method saves an employee.
     *
     * @param employee an employee object which should be saved
     * @return employee domain object
     */
    Employee save(Employee employee);

    /**
     * Method updates information about employee.
     *
     * @param employeeId company id
     * @param employee   employee domain object
     * @return employee domain object
     * @throws EntityNotFoundException       if a company does not exist
     * @throws IdMismatchingException        if Ids in URL and payload are not equal
     */
    Employee update(Long employeeId, Employee employee)
            throws EntityNotFoundException, IdMismatchingException;

    /**
     * Method deletes an employee by the given id.
     *
     * @param employeeId id of an employee
     * @throws EntityNotFoundException if employee does not exist
     */
    void delete(Long employeeId) throws EntityNotFoundException;

}
