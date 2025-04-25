package com.prueba.tecnica.banking.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(
            name = "account_seq",
            sequenceName = "account_seq", // nombre real en la DB
            initialValue = 22001,
            allocationSize = 1
    )
    @Column(name = "account_number")
    private Long accountNumber;
    @Column(name = "account_type")
    private String accountType;
    private Double balance;
    @Column(name = "account_status")
    private Boolean accountStatus;
    @ManyToOne
    @JoinColumn(name = "identification", referencedColumnName = "identification")
    private Customer customer;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Movements> movements = new ArrayList<>();

}
