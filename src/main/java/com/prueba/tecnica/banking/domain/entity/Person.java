package com.prueba.tecnica.banking.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Id
    @Column(name = "identification", nullable = false, unique = true)
    @NotNull @NotEmpty
    private String identification;
    @NotNull @NotEmpty
    private String name;
    @NotNull
    private String gender;
    @NotNull
    private Integer age;
    @NotNull
    private String address;
    @NotNull
    private String phone;

}
