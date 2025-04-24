package com.prueba.tecnica.banking.service;

import com.prueba.tecnica.banking.domain.entity.Account;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;

public interface AccountService {
    Account saveAccount(Account account, CommonHeaders commonHeaders);
    Account updateAccount(Account account, CommonHeaders commonHeaders);
}
