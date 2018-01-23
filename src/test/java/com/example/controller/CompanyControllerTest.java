package com.example.controller;

import com.example.dto.CompanyCreateDto;
import com.example.dto.CompanyDto;
import com.example.exception.ConstraintsViolationException;
import com.example.exception.EntityNotFoundException;
import com.example.model.Company;
import com.example.service.api.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

public class CompanyControllerTest {

    private static final String COMPANY_NAME_1 = "Apple";

    private static final String COMPANY_NAME_2 = "Google";

    private static final Long COMPANY_ID_1 = 1L;

    private static final Long COMPANY_ID_2 = 2L;

    @Mock
    private CompanyService companyService;

    private CompanyController controller;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CompanyController(companyService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testList_withResults() throws Exception {
        // given
        Company company1 = createCompany(COMPANY_ID_1, COMPANY_NAME_1);
        Company company2 = createCompany(COMPANY_ID_2, COMPANY_NAME_2);
        List<Company> companies = Arrays.asList(company1, company2);

        // when
        when(companyService.list()).thenReturn(companies);
        MvcResult result = mockMvc.perform(get("/api/company"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<CompanyDto> actual = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should should have correct amount of items", 2, actual.size());
        verify(companyService, times(1)).list();
    }

    @Test
    public void testFind_withResults() throws Exception {
        // given
        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_1);

        // when
        when(companyService.find(anyLong())).thenReturn(company);
        MvcResult result = mockMvc.perform(get("/api/company/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        CompanyDto actual = mapper.readValue(result.getResponse().getContentAsString(), CompanyDto.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should have correct id", company.getId(), actual.getId());
        assertEquals("result should have correct name", company.getName(), actual.getName());
        verify(companyService, times(1)).find(eq(COMPANY_ID_1));

    }

    @Test
    public void testFind_EntityNotFoundException() throws Exception {
        // when
        when(companyService.find(anyLong())).thenThrow(new EntityNotFoundException("not found"));
        mockMvc.perform(get("/api/company/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        //then
        verify(companyService, times(1)).find(eq(COMPANY_ID_1));

    }

    @Test
    public void testDelete_successful() throws Exception {
        // when
        doNothing().when(companyService).delete(anyLong());
        mockMvc.perform(delete("/api/company/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete_EntityNotFoundException() throws Exception {
        // when
        doThrow(new EntityNotFoundException("not found")).when(companyService).delete(anyLong());
        mockMvc.perform(delete("/api/company/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        //then
        verify(companyService, times(1)).delete(eq(COMPANY_ID_1));

    }

    @Test
    public void testCreate_successful() throws Exception {
        // given
        CompanyCreateDto dto = new CompanyCreateDto();
        dto.setName(COMPANY_NAME_1);
        String json = mapper.writeValueAsString(dto);

        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_1);

        // when
        when(companyService.save(any(Company.class))).thenReturn(company);
        MvcResult result = mockMvc.perform(post("/api/company").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        CompanyDto actual = mapper.readValue(result.getResponse().getContentAsString(), CompanyDto.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should have correct id", company.getId(), actual.getId());
        assertEquals("result should have correct name", company.getName(), actual.getName());
        verify(companyService, times(1)).save(any(Company.class));
    }

    @Test
    public void testCreate_ConstraintsViolationException() throws Exception {
        // given
        CompanyCreateDto dto = new CompanyCreateDto();
        String json = mapper.writeValueAsString(dto);

        // when
        when(companyService.save(any(Company.class))).thenThrow(new ConstraintsViolationException("exception"));
        mockMvc.perform(post("/api/company").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //then
        verify(companyService, times(1)).save(any(Company.class));
    }

    @Test
    public void testUpdate_successful() throws Exception {
        // given
        CompanyDto dto = createCompanyDto(COMPANY_ID_1, COMPANY_NAME_2);
        dto.setName(COMPANY_NAME_1);
        String json = mapper.writeValueAsString(dto);

        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_2);

        // when
        when(companyService.update(anyLong(), any(Company.class))).thenReturn(company);
        MvcResult result = mockMvc.perform(put("/api/company/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        CompanyDto actual = mapper.readValue(result.getResponse().getContentAsString(), CompanyDto.class);

        //then
        assertNotNull("result should be not null", actual);
        assertEquals("result should have correct id", company.getId(), actual.getId());
        assertEquals("result should have correct name", company.getName(), actual.getName());
        verify(companyService, times(1)).update(eq(COMPANY_ID_1), any(Company.class));
    }

    @Test
    public void testUpdate_EntityNotFoundException() throws Exception {
        // given
        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_2);
        String json = mapper.writeValueAsString(company);

        // when
        when(companyService.update(anyLong(), any(Company.class))).thenThrow(new EntityNotFoundException("exception"));
        mockMvc.perform(put("/api/company/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        //then
        verify(companyService, times(1)).update(eq(COMPANY_ID_1), eq(company));
    }

    @Test
    public void testUpdate_ConstraintsViolationException() throws Exception {
        // given
        CompanyDto dto = createCompanyDto(COMPANY_ID_1, null);
        String json = mapper.writeValueAsString(dto);

        // when
        when(companyService.update(anyLong(), any(Company.class)))
                .thenThrow(new ConstraintsViolationException("exception"));
        mockMvc.perform(put("/api/company/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        verify(companyService, times(1)).update(eq(COMPANY_ID_1), any(Company.class));
    }

    private Company createCompany(Long id, String name) {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        return company;
    }

    private CompanyDto createCompanyDto(Long id, String name) {
        CompanyDto dto = new CompanyDto();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }

}