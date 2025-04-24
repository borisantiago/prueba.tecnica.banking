package com.prueba.tecnica.banking.service;

import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.service.impl.CustomerServiceImpl;

import java.time.LocalDate;

public interface CustomerService {
    Customer saveCustomer(Customer customer, CommonHeaders commonHeaders);
    Customer updateCustomer(Customer customer, CommonHeaders commonHeaders);
    CustomerServiceImpl.CustomerResponseDTO findCustomerWithAccountsAndMovements(Customer customer, CommonHeaders commonHeaders);

    CustomerServiceImpl.CustomerResponseDTO findCustomerWithMovementsBetweenDates(String identification, LocalDate startDate, LocalDate endDate, CommonHeaders commonHeaders);
}
