package com.example.controller;

import com.example.model.Company;
import com.example.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CompanyController {

    CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/api/company")
    @ResponseBody
    public List<Company> list(){
        return (List<Company>) companyRepository.findAll();
    }

    @GetMapping("/api/company/{companyId}")
    @ResponseBody
    public Company list(@PathVariable("companyId") Long companyId){
        return companyRepository.findOne(companyId);
    }
}
