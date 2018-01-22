package com.example;

import com.example.model.Company;
import com.example.repository.CompanyRepository;
import org.jfairy.Fairy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private CompanyRepository repository;

    private static final int NUMBER_OF_RANDOM_COMPANIES = 10;

    @Autowired
    public DatabaseLoader(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {

        Fairy fairy = Fairy.create();

        for(int i = 0; i< NUMBER_OF_RANDOM_COMPANIES; i++){
            org.jfairy.producer.company.Company fakeCompany = fairy.company();
            this.repository.save(new Company(fakeCompany.name()));
        }

    }
}
