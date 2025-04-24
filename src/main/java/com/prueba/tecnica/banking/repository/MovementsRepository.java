package com.prueba.tecnica.banking.repository;

import com.prueba.tecnica.banking.domain.entity.Movements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovementsRepository extends JpaRepository<Movements, Long> {
    Optional<Movements> findById(Long id);

}
