package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Company(String name) {
        this.name = name;
    }
}
