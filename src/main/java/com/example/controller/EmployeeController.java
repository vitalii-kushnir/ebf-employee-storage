package com.example.controller;

import com.example.dto.EmployeeCreateDto;
import com.example.dto.EmployeeDto;
import com.example.dto.EmployeeListDto;
import com.example.dto.EmployeeUpdateDto;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.model.Employee;
import com.example.service.api.CompanyService;
import com.example.service.api.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for the employee API.
 */
@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    private CompanyService companyService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    /**
     * Method returns list of employees.
     *
     * @return list of employees
     */
    @GetMapping("/api/employee/company/{companyId}")
    @ResponseBody
    public List<EmployeeListDto> findEmployeesByCompany(@PathVariable("companyId") Long companyId)
            throws EntityNotFoundException {
        List<Employee> employees = employeeService.findEmployeesOfCompany(companyId);
        return makeEmployeeListDto(employees);
    }

    /**
     * Method returns employee with a given id.
     *
     * @param employeeId employee id
     * @return employee DTO
     * @throws EntityNotFoundException if employee does not exist
     */
    @GetMapping("/api/employee/{employeeId}")
    @ResponseBody
    public EmployeeDto find(@PathVariable Long employeeId) throws EntityNotFoundException {
        Employee employee = employeeService.find(employeeId);
        return makeEmployeeDto(employee);
    }

    /**
     * Method updates employee with the given id.
     *
     * @param employeeId company id
     * @param dto        DTO with new employee data
     * @return employee DTO
     * @throws EntityNotFoundException       if employee does not exist
     * @throws IdMismatchingException        if Ids in URL and payload are not equal
     */
    @PutMapping("/api/employee/{employeeId}")
    @ResponseBody
    public EmployeeDto update(@PathVariable Long employeeId, @Valid @RequestBody EmployeeUpdateDto dto)
            throws EntityNotFoundException, IdMismatchingException {
        Employee employee = employeeService.update(employeeId, makeEmployee(dto));
        return makeEmployeeDto(employee);
    }

    /**
     * Method creates a new employee.
     *
     * @param dto new employee DTO
     * @return employee DTO
     * @throws EntityNotFoundException       if entity does not exist
     */
    @PostMapping("/api/employee")
    @ResponseBody
    public EmployeeDto create(@Valid @RequestBody EmployeeCreateDto dto) throws  EntityNotFoundException {
        Employee employee = employeeService.save(makeEmployee(dto));
        return makeEmployeeDto(employee);
    }

    /**
     * Method deletes employee with the given id.
     *
     * @param employeeId employee id
     * @throws EntityNotFoundException if employee does not exist
     */
    @DeleteMapping("/api/employee/{employeeId}")
    @ResponseBody
    public void delete(@PathVariable Long employeeId) throws EntityNotFoundException {
        employeeService.delete(employeeId);
    }

    private EmployeeDto makeEmployeeDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSurname(employee.getSurname());
        dto.setEmail(employee.getEmail());
        dto.setSalary(employee.getSalary());

        dto.setCompanyId(employee.getCompany().getId());
        dto.setCompanyName(employee.getCompany().getName());

        dto.setAddress(employee.getAddress());
        return dto;
    }

    private Employee makeEmployee(EmployeeCreateDto dto) throws EntityNotFoundException {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setAddress(dto.getAddress());

        Company company = companyService.find(dto.getCompanyId());
        employee.setCompany(company);

        return employee;
    }

    private Employee makeEmployee(EmployeeUpdateDto dto) throws EntityNotFoundException {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setAddress(dto.getAddress());

        Company company = companyService.find(dto.getCompanyId());
        employee.setCompany(company);

        return employee;
    }

    private List<EmployeeListDto> makeEmployeeListDto(Collection<Employee> employees) {
        return employees.stream()
                .map(e -> {
                    EmployeeListDto dto = new EmployeeListDto();
                    dto.setId(e.getId());
                    dto.setName(e.getName());
                    dto.setSurname(e.getSurname());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
