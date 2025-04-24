package com.prueba.tecnica.banking.repository;

import com.prueba.tecnica.banking.domain.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdentification(String identification);
    List<Customer> findByStatus(Boolean status);
    @EntityGraph(attributePaths = {"accounts"})
    Optional<Customer> findWithAccountsByIdentification(String identification);


}
