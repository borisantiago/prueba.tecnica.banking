package com.prueba.tecnica.banking.service;

import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;

public interface CustomerService {
    Customer saveCustomer(Customer customer, CommonHeaders commonHeaders);
    Customer updateCustomer(Customer customer, CommonHeaders commonHeaders);
}
