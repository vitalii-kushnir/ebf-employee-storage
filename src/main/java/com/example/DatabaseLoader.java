package com.example;

import com.example.model.Company;
import com.example.model.Employee;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import org.jfairy.Fairy;
import org.jfairy.producer.person.Address;
import org.jfairy.producer.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private static final int NUMBER_OF_RANDOM_COMPANIES = 5;

    private static final int NUMBER_OF_RANDOM_EMPLOTEES = 5;

    private CompanyRepository companyRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public DatabaseLoader(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... strings) {

        Fairy fairy = Fairy.create();

        for (int i = 0; i < NUMBER_OF_RANDOM_COMPANIES; i++) {
            org.jfairy.producer.company.Company fakeCompany = fairy.company();
            Company company = companyRepository.save(new Company(fakeCompany.name()));

            for (int j = 0; j < NUMBER_OF_RANDOM_EMPLOTEES; j++) {

                Person fakePerson = fairy.person();
                Address fakeAddress = fakePerson.getAddress();

                double random = Math.random() * 100000 + 1;
                BigDecimal salary = BigDecimal.valueOf(random);

                Employee employee = new Employee();
                employee.setAddress(fakeAddress.toString());
                employee.setCompany(company);
                employee.setEmail(fakePerson.companyEmail());
                employee.setName(fakePerson.firstName());
                employee.setSurname(fakePerson.lastName());
                employee.setSalary(salary);

                employeeRepository.save(employee);
            }

        }

    }
}
