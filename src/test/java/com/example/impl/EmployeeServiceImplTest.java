package com.example.impl;

import com.example.exception.ConstraintsViolationException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.model.Employee;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import com.example.service.impl.EmployeeServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {

    private static final Long EMPLOYEE_ID_1 = 1L;

    private static final Long EMPLOYEE_ID_2 = 2L;

    private static final String EMPLOYEE_NAME_1 = "Tony";

    private static final String EMPLOYEE_NAME_2 = "Bob";

    private static final Long COMPANY_ID = 1L;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CompanyRepository companyRepository;

    private EmployeeServiceImpl employeeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepository, companyRepository);
    }

    @Test
    public void testFind_employeeExists() throws EntityNotFoundException {
        //given
        Employee employee = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_1);

        //when
        when(employeeRepository.findOne(any(Long.class))).thenReturn(employee);
        Employee actual = employeeService.find(EMPLOYEE_ID_1);

        //then
        assertNotNull("result cannot be not null", actual);
        assertEquals("it should return correct result", employee, actual);
        verify(employeeRepository, times(1)).findOne(eq(EMPLOYEE_ID_1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFind_withEntityNotFoundException() throws EntityNotFoundException {
        //when
        when(employeeRepository.findOne(any(Long.class))).thenReturn(null);
        employeeService.find(EMPLOYEE_ID_1);
    }

    @Test
    public void testDelete_successfulExecution() throws EntityNotFoundException {
        //given
        Employee employee = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_1);

        //when
        when(employeeRepository.findOne(any(Long.class))).thenReturn(employee);
        doNothing().when(employeeRepository).delete(any(Employee.class));
        employeeService.delete(EMPLOYEE_ID_1);

        //then
        verify(employeeRepository, times(1)).findOne(eq(EMPLOYEE_ID_1));
        verify(employeeRepository, times(1)).delete(eq(employee));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDelete_withEntityNotFoundException() throws EntityNotFoundException {
        //when
        when(employeeRepository.findOne(any(Long.class))).thenReturn(null);
        employeeService.delete(EMPLOYEE_ID_1);
    }

    @Test
    public void testFindEmployeesByCompany_withResult() throws EntityNotFoundException {
        //given
        Employee employee1 = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_1);
        Employee employee2 = createEmployee(EMPLOYEE_ID_2, EMPLOYEE_NAME_2);
        List<Employee> result = Lists.newArrayList(employee1, employee2);
        Company company = createCompany(COMPANY_ID);

        //when
        when(employeeRepository.findEmployeesByCompany(any(Company.class))).thenReturn(result);
        when(companyRepository.findOne(anyLong())).thenReturn(company);
        List<Employee> actual = employeeService.findEmployeesOfCompany(COMPANY_ID);

        //then
        assertNotNull("result cannot be null", actual);
        assertEquals("result contains correct amount of items", 2, actual.size());
        assertTrue("result contains the 1st company", actual.contains(employee1));
        assertTrue("result contains the 2d company", actual.contains(employee2));
        verify(employeeRepository, times(1)).findEmployeesByCompany(eq(company));
        verify(companyRepository, times(1)).findOne(anyLong());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindEmployeesByCompany_companyNotExist_EntityNotFoundException() throws EntityNotFoundException {
        //when
        when(companyRepository.findOne(anyLong())).thenReturn(null);
        employeeService.findEmployeesOfCompany(COMPANY_ID);
    }

    @Test
    public void testSave_withoutExeptions() throws ConstraintsViolationException {
        //given
        Employee newEmployee = createEmployee(null, EMPLOYEE_NAME_1);
        Employee createdEmployee = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_1);

        //when
        when(employeeRepository.save(any(Employee.class))).thenReturn(createdEmployee);
        Employee actual = employeeRepository.save(newEmployee);

        //then
        assertNotNull("result cannot be null", actual);
        assertEquals("should update entity", createdEmployee, actual);
        verify(employeeRepository, times(1)).save(eq(newEmployee));
    }

    @Test
    public void testUpdate_withoutExeptions() throws Exception {
        //given
        Employee employee = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_2);
        Employee createdEmployee = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_2);

        //when
        when(employeeRepository.findOne(any(Long.class))).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(createdEmployee);
        Employee actual = employeeService.update(EMPLOYEE_ID_1, employee);

        //then
        assertNotNull("result cannot be null", actual);
        assertEquals("should update entity", createdEmployee, actual);
        verify(employeeRepository, times(1)).findOne(eq(EMPLOYEE_ID_1));
        verify(employeeRepository, times(1)).save(eq(employee));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdate_withEntityNotFoundException() throws Exception {
        //given
        Employee employee = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_2);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(null);
        employeeService.update(EMPLOYEE_ID_1, employee);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdate_withConstraintsViolationException() throws Exception {
        //given
        Employee employee = createEmployee(EMPLOYEE_ID_1, EMPLOYEE_NAME_1);

        //when
        when(employeeRepository.findOne(any(Long.class))).thenReturn(null);
        employeeService.update(EMPLOYEE_ID_1, employee);
    }

    @Test(expected = IdMismatchingException.class)
    public void testUpdate_withIdMismatchingException() throws Exception {
        //given
        Employee employee = createEmployee(EMPLOYEE_ID_2, EMPLOYEE_NAME_2);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(null);
        employeeService.update(EMPLOYEE_ID_1, employee);
    }

    private Employee createEmployee(Long id, String name) {
        Company company = createCompany(COMPANY_ID);

        Employee emplayee = new Employee();
        emplayee.setId(id);
        emplayee.setName(name);
        emplayee.setSurname("surname");
        emplayee.setSalary(BigDecimal.TEN);
        emplayee.setEmail("email@email.com");
        emplayee.setCompany(company);
        emplayee.setAddress("fake address");
        return emplayee;
    }

    private Company createCompany(Long id) {
        Company company = new Company();
        company.setId(id);
        return company;
    }

}