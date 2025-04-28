package com.prueba.tecnica.banking.service;

import com.prueba.tecnica.banking.domain.CustomerMapper;
import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.service.impl.CustomerServiceImpl;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {
    List<CustomerServiceImpl.CustomerResponseDTO> findCustomers(CommonHeaders commonHeaders);
    Customer saveCustomer(Customer customer, CommonHeaders commonHeaders);
    Customer updateCustomer(Customer customer, CommonHeaders commonHeaders);
    CustomerServiceImpl.CustomerResponseDTO findCustomerWithAccountsAndMovements(Customer customer, CommonHeaders commonHeaders);

    CustomerMapper.CustomerResponseMapper findCustomerWithMovementsBetweenDates(String identification, LocalDate startDate, LocalDate endDate, CommonHeaders commonHeaders);
    Void deleteCustomerForId(String identification, CommonHeaders commonHeaders);
}
