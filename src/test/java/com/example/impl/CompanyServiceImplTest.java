package com.example.impl;

import com.example.exception.ConstraintsViolationException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IdMismatchingException;
import com.example.model.Company;
import com.example.repository.CompanyRepository;
import com.example.service.impl.CompanyServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompanyServiceImplTest {

    private static final String COMPANY_NAME_1 = "Apple";

    private static final String COMPANY_NAME_2 = "Google";

    private static final Long COMPANY_ID_1 = 1L;

    private static final Long COMPANY_ID_2 = 2L;

    @Mock
    private CompanyRepository companyRepository;

    private CompanyServiceImpl companyService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ValidatorFactory validationFactory = Validation.buildDefaultValidatorFactory();
        companyService = new CompanyServiceImpl(companyRepository, validationFactory);
    }

    @Test
    public void testFind_companyExists() throws EntityNotFoundException {
        //given
        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_1);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(company);
        Company actual = companyService.find(COMPANY_ID_1);

        //then
        assertNotNull("result cannot be null", actual);
        assertEquals("should return correct result", company, actual);
        verify(companyRepository, times(1)).findOne(eq(COMPANY_ID_1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFind_withEntityNotFoundException() throws EntityNotFoundException {
        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(null);
        companyService.find(COMPANY_ID_1);
    }

    @Test
    public void testDelete_successfulExecution() throws EntityNotFoundException {
        //given
        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_1);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(company);
        doNothing().when(companyRepository).delete(any(Company.class));
        companyService.delete(COMPANY_ID_1);

        //then
        verify(companyRepository, times(1)).findOne(eq(COMPANY_ID_1));
        verify(companyRepository, times(1)).delete(eq(company));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDelete_withEntityNotFoundException() throws EntityNotFoundException {
        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(null);
        companyService.delete(COMPANY_ID_1);
    }

    @Test
    public void testList_withResult() {
        //given
        Company company1 = createCompany(COMPANY_ID_1, COMPANY_NAME_1);
        Company company2 = createCompany(COMPANY_ID_2, COMPANY_NAME_2);
        List<Company> result = Lists.newArrayList(company1, company2);

        //when
        when(companyRepository.findAll()).thenReturn(result);
        List<Company> actual = companyService.list();

        //then
        assertNotNull("result cannot be null", actual);
        assertEquals("result contains correct amount of items", 2, actual.size());
        assertTrue("result contains the 1st company", actual.contains(company1));
        assertTrue("result contains the 2d company", actual.contains(company2));
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    public void testSave_withoutExeptions() throws ConstraintsViolationException {
        //given
        Company newCompany = createCompany(null, COMPANY_NAME_1);
        Company createdCompany = createCompany(COMPANY_ID_1, COMPANY_NAME_1);

        //when
        when(companyRepository.save(any(Company.class))).thenReturn(createdCompany);
        Company actual = companyService.save(newCompany);

        //then
        assertNotNull("result cannot be null", actual);
        assertEquals("should update entity", createdCompany, actual);
        verify(companyRepository, times(1)).save(eq(newCompany));
    }

    @Test(expected = ConstraintsViolationException.class)
    public void testSave_withConstraintsViolationException() throws ConstraintsViolationException {
        //given
        Company newCompany = createCompany(null, null);

        //when
        companyService.save(newCompany);
    }

    @Test
    public void testUpdate_withoutExeptions() throws Exception {
        //given
        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_1);
        Company updatedCompany = createCompany(COMPANY_ID_1, COMPANY_NAME_2);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(company);
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);
        Company actual = companyService.update(COMPANY_ID_1, company);

        //then
        assertNotNull("result cannot be null", actual);
        assertEquals("should update entity", updatedCompany, actual);
        verify(companyRepository, times(1)).findOne(eq(COMPANY_ID_1));
        verify(companyRepository, times(1)).save(eq(company));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdate_withEntityNotFoundException() throws Exception {
        //given
        Company company = createCompany(COMPANY_ID_1, COMPANY_NAME_2);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(null);
        companyService.update(COMPANY_ID_1, company);
    }

    @Test(expected = ConstraintsViolationException.class)
    public void testUpdate_withConstraintsViolationException() throws Exception {
        //given
        Company targetCompany = createCompany(COMPANY_ID_1, null);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(targetCompany);
        companyService.update(COMPANY_ID_1, targetCompany);
    }

    @Test(expected = IdMismatchingException.class)
    public void testUpdate_withIdMismatchingException() throws Exception {
        //given
        Company company = createCompany(COMPANY_ID_2, COMPANY_NAME_2);

        //when
        when(companyRepository.findOne(any(Long.class))).thenReturn(null);
        companyService.update(COMPANY_ID_1, company);
    }

    private Company createCompany(Long id, String name) {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        return company;
    }
}