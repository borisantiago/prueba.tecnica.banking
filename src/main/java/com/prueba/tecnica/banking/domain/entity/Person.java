package com.prueba.tecnica.banking.domain.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public class Person {
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;

}
