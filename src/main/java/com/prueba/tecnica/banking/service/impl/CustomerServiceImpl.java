package com.prueba.tecnica.banking.service.impl;

import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.exception.BankingException;
import com.prueba.tecnica.banking.repository.CustomerRepository;
import com.prueba.tecnica.banking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer, CommonHeaders commonHeaders) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer, CommonHeaders commonHeaders) {
        Customer user = customerRepository.findByIdentification(customer.getIdentification())
                .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(), "customer not found"));
        user.setCustomerId(customer.getCustomerId());
        user.setPassword(customer.getPassword());
        user.setStatus(customer.getStatus());
        user.setIdentification(customer.getIdentification());
        user.setName(customer.getName());
        user.setGender(customer.getGender());
        user.setAge(customer.getAge());
        user.setAddress(customer.getAddress());
        user.setPhone(customer.getPhone());
        return customerRepository.save(user);
    }

}
