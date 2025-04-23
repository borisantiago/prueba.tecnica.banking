package com.prueba.tecnica.banking.controller;

import com.prueba.tecnica.banking.domain.dto.SaveRecordResponse;
import com.prueba.tecnica.banking.domain.entity.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${properties.base-path}")
public class CustomerController {
    public Mono<ResponseEntity<SaveRecordResponse>> saveCustomer(@RequestBody Customer customer){
        return null;
    }
}
