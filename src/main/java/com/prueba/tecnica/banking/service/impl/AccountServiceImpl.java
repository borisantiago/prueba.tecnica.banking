package com.prueba.tecnica.banking.service.impl;

import com.prueba.tecnica.banking.domain.entity.Account;
import com.prueba.tecnica.banking.domain.entity.Customer;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.exception.BankingException;
import com.prueba.tecnica.banking.repository.AccountRepository;
import com.prueba.tecnica.banking.repository.CustomerRepository;
import com.prueba.tecnica.banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Account saveAccount(Account account, CommonHeaders commonHeaders) {
        customerRepository.findByIdentification(account.getCustomer().getIdentification())
                .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(),
                        "Account not created because the person's ID card could not be found"));
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account, CommonHeaders commonHeaders) {
        Account account_edit = accountRepository.findByAccountNumber(account.getAccountNumber())
                .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(), "Account not found"));
        account_edit.setAccountNumber(account.getAccountNumber());
        account_edit.setAccountType(account.getAccountType());
        account_edit.setBalance(account.getBalance());
        account_edit.setAccountStatus(account.getAccountStatus());

        if (account.getCustomer() != null && account.getCustomer().getIdentification() != null) {
            Customer customer = customerRepository.findByIdentification(account.getCustomer().getIdentification())
                    .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(), "Customer not found"));
            account.setCustomer(customer);
        }

        return accountRepository.save(account_edit);
    }

}
