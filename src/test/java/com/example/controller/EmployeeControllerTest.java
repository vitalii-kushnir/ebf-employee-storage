package com.example.controller;

import com.example.dto.CompanyCreateDto;
import com.example.dto.CompanyDto;
import com.example.dto.EmployeeCreateDto;
import com.example.dto.EmployeeDto;
import com.example.dto.EmployeeUpdateDto;
import com.example.exception.EntityNotFoundException;
import com.example.model.Company;
import com.example.model.Employee;
import com.example.service.api.CompanyService;
import com.example.service.api.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfairy.Fairy;
import org.jfairy.producer.person.Address;
import org.jfairy.producer.person.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EmployeeControllerTest {

    private static final String COMPANY_NAME_1 = "Apple";

    private static final String COMPANY_NAME_2 = "Google";

    private static final Long COMPANY_ID_1 = 1L;

    private static final Long COMPANY_ID_2 = 2L;

    private static final Long EMPLOYEE_ID_1 = 1L;

    private static final Long EMPLOYEE_ID_2 = 2L;
    private static final String EMAIL_EMAIL = "test@test.com";
    private static final String EMAIL_NAME = "Name";
    private static final String EMAIL_SURNAME = "Surmane";
    private static final String EMAIL_ADDRESS = "address";
    private static final BigDecimal EMAIL_SALARY = BigDecimal.TEN;

    private static final String EMAIL_NEW_NAME = "New Name";

    @Mock
    private CompanyService companyService;

    @Mock
    private EmployeeService employeeService;

    private EmployeeController controller;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new EmployeeController(employeeService, companyService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testFindEmployeesByCompany_withResults() throws Exception {
        // given
        Employee employee1 = createEmployee(EMPLOYEE_ID_1, COMPANY_ID_1, COMPANY_NAME_1);
        Employee employee2 = createEmployee(EMPLOYEE_ID_1, COMPANY_ID_1, COMPANY_NAME_1);
        List<Employee> employees = Arrays.asList(employee1, employee2);

        // when
        when(employeeService.findEmployeesOfCompany(anyLong())).thenReturn(employees);
        MvcResult result = mockMvc.perform(get("/api/employee/company/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<EmployeeDto> actual = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should should have correct amount of items", 2, actual.size());
        verify(employeeService, times(1)).findEmployeesOfCompany(eq(COMPANY_ID_1));
    }

    @Test
    public void testFind_withResults() throws Exception {
        // given
        Employee employee = createEmployee(EMPLOYEE_ID_1, COMPANY_ID_1, COMPANY_NAME_1);

        // when
        when(employeeService.find(anyLong())).thenReturn(employee);
        MvcResult result = mockMvc.perform(get("/api/employee/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        EmployeeDto actual = mapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should have correct id", employee.getId(), actual.getId());
        assertEquals("result should have correct name", employee.getName(), actual.getName());
        assertEquals("result should have correct surname", employee.getSurname(), actual.getSurname());
        assertEquals("result should have correct email", employee.getEmail(), actual.getEmail());
        assertEquals("result should have correct salary", employee.getSalary(), actual.getSalary());
        assertEquals("result should have correct company id", employee.getCompany().getId(), actual.getCompanyId());
        assertEquals("result should have correct company name", employee.getCompany().getName(), actual.getCompanyName());
        assertEquals("result should have correct company address", employee.getAddress(), actual.getAddress());
        verify(employeeService, times(1)).find(eq(EMPLOYEE_ID_1));

    }

    @Test
    public void testFind_EntityNotFoundException() throws Exception {
        // when
        when(employeeService.find(anyLong())).thenThrow(new EntityNotFoundException("not found"));
        mockMvc.perform(get("/api/employee/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        //then
        verify(employeeService, times(1)).find(eq(EMPLOYEE_ID_1));

    }

    @Test
    public void testDelete_successful() throws Exception {
        // when
        doNothing().when(employeeService).delete(anyLong());
        mockMvc.perform(delete("/api/employee/1"))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        verify(employeeService, times(1)).delete(eq(EMPLOYEE_ID_1));
    }

    @Test
    public void testDelete_EntityNotFoundException() throws Exception {
        // when
        doThrow(new EntityNotFoundException("not found")).when(employeeService).delete(anyLong());
        mockMvc.perform(delete("/api/employee/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        //then
        verify(employeeService, times(1)).delete(eq(EMPLOYEE_ID_1));

    }

    @Test
    public void testCreate_successful() throws Exception {
        // given
        EmployeeCreateDto dto = createEmployeeCreateDto(COMPANY_ID_1, COMPANY_NAME_1);

        String json = mapper.writeValueAsString(dto);

        Employee employee = createEmployee(EMPLOYEE_ID_1, COMPANY_ID_1, COMPANY_NAME_1);

        // when
        when(employeeService.save(any(Employee.class))).thenReturn(employee);
        MvcResult result = mockMvc.perform(post("/api/employee").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        EmployeeDto actual = mapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should have correct id", employee.getId(), actual.getId());
        assertEquals("result should have correct name", employee.getName(), actual.getName());
        assertEquals("result should have correct surname", employee.getSurname(), actual.getSurname());
        assertEquals("result should have correct email", employee.getEmail(), actual.getEmail());
        assertEquals("result should have correct salary", employee.getSalary(), actual.getSalary());
        assertEquals("result should have correct company id", employee.getCompany().getId(), actual.getCompanyId());
        assertEquals("result should have correct company name", employee.getCompany().getName(), actual.getCompanyName());
        assertEquals("result should have correct company address", employee.getAddress(), actual.getAddress());
        verify(employeeService, times(1)).save(any(Employee.class));
    }

    @Test
    public void testUpdate_successful() throws Exception {
        // given
        EmployeeUpdateDto dto = createEmployeeUpdateDto(EMPLOYEE_ID_1, COMPANY_ID_1, COMPANY_NAME_1);
        dto.setName(EMAIL_NEW_NAME);
        String json = mapper.writeValueAsString(dto);

        Employee employee = createEmployee(EMPLOYEE_ID_1, COMPANY_ID_1, COMPANY_NAME_1);
        employee.setName(EMAIL_NEW_NAME);

        // when
        when(employeeService.update(anyLong(), any(Employee.class))).thenReturn(employee);
        MvcResult result = mockMvc.perform(put("/api/employee/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        EmployeeDto actual = mapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should have correct id", employee.getId(), actual.getId());
        assertEquals("result should have correct name", employee.getName(), actual.getName());
        assertEquals("result should have correct surname", employee.getSurname(), actual.getSurname());
        assertEquals("result should have correct email", employee.getEmail(), actual.getEmail());
        assertEquals("result should have correct salary", employee.getSalary(), actual.getSalary());
        assertEquals("result should have correct company id", employee.getCompany().getId(), actual.getCompanyId());
        assertEquals("result should have correct company name", employee.getCompany().getName(), actual.getCompanyName());
        assertEquals("result should have correct company address", employee.getAddress(), actual.getAddress());
        verify(employeeService, times(1)).update(eq(EMPLOYEE_ID_1), any(Employee.class));
    }

    @Test
    public void testUpdate_EntityNotFoundException() throws Exception {
        // given
        EmployeeUpdateDto dto = createEmployeeUpdateDto(EMPLOYEE_ID_1, COMPANY_ID_1, COMPANY_NAME_1);
        String json = mapper.writeValueAsString(dto);

        // when
        when(employeeService.update(anyLong(), any(Employee.class))).thenThrow(new EntityNotFoundException("exception"));
        mockMvc.perform(put("/api/employee/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        //then
        verify(employeeService, times(1)).update(eq(EMPLOYEE_ID_1), any(Employee.class));
    }

    private Company createCompany(Long id, String name) {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        return company;
    }

    private Employee createEmployee(Long employeeId, Long companyId, String companyName) {
        Company company = createCompany(companyId, companyName);

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setAddress(EMAIL_ADDRESS);
        employee.setCompany(company);
        employee.setEmail(EMAIL_EMAIL);
        employee.setName(EMAIL_NAME);
        employee.setSurname(EMAIL_SURNAME);
        employee.setSalary(EMAIL_SALARY);

        return employee;
    }

    private EmployeeUpdateDto createEmployeeUpdateDto(Long employeeId, Long companyId, String companyName) {
        Employee employee = createEmployee(employeeId, companyId, companyName);
        EmployeeUpdateDto dto = new EmployeeUpdateDto();

        dto.setId(employee.getId());
        dto.setAddress(EMAIL_ADDRESS);
        dto.setCompanyId(employee.getCompany().getId());
        dto.setEmail(EMAIL_EMAIL);
        dto.setName(EMAIL_NAME);
        dto.setSurname(EMAIL_SURNAME);
        dto.setSalary(EMAIL_SALARY);

        return dto;
    }

    private EmployeeCreateDto createEmployeeCreateDto( Long companyId, String companyName) {
        Employee employee = createEmployee(null, companyId, companyName);
        EmployeeCreateDto dto = new EmployeeUpdateDto();

        dto.setAddress(EMAIL_ADDRESS);
        dto.setCompanyId(employee.getCompany().getId());
        dto.setEmail(EMAIL_EMAIL);
        dto.setName(EMAIL_NAME);
        dto.setSurname(EMAIL_SURNAME);
        dto.setSalary(EMAIL_SALARY);

        return dto;
    }

}