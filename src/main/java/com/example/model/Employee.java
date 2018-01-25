package com.example.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name can not be null!")
    private String name;

    @Column(name = "surname")
    @NotNull(message = "Surname can not be null!")
    private String surname;

    @Column(name = "email")
    @NotNull(message = "Email can not be null!")
    private String email;

    @Column(name = "salary")
    @Min(message = "Salary cannot be less then 0!", value = 0)
    @NotNull(message = "Salary can not be null!")
    private BigDecimal salary;

    @NotNull(message = "Company can not be null!")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @Column(name = "address")
    @NotNull(message = "Address can not be null!")
    private String address;
}
