package com.example.repository;

import com.example.model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}
