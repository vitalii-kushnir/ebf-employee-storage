package com.example.repository;

import com.example.model.Company;
import com.example.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository for working with employees.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    /**
     * Method returns a list of employees of the given company.
     *
     * @param company company instance
     * @return list of employees
     */
    List<Employee> findEmployeesByCompany(Company company);

    /**
     * Method calculates the average salary in the given company
     *
     * @param company company instance
     * @return average salary
     */
    @Query(value = "SELECT AVG (salary) FROM Employee WHERE company_id=?1", nativeQuery = true)
    BigDecimal getAverageSalaryForCompany(Company company);
}
