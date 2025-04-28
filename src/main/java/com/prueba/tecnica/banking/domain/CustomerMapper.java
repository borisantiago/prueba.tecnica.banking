package com.prueba.tecnica.banking.domain;

import com.prueba.tecnica.banking.service.impl.CustomerServiceImpl;
import lombok.Data;

import java.util.List;

public class CustomerMapper {

    @Data
    public static class CustomerResponseMapper {
        private String identification;
        private String name;
        private Boolean status;
        private List<CustomerServiceImpl.AccountDTO> accounts;
    }
}
