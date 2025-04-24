package com.prueba.tecnica.banking.service.impl;

import com.prueba.tecnica.banking.domain.entity.Account;
import com.prueba.tecnica.banking.domain.entity.Movements;
import com.prueba.tecnica.banking.domain.models.CommonHeaders;
import com.prueba.tecnica.banking.exception.BankingException;
import com.prueba.tecnica.banking.repository.AccountRepository;
import com.prueba.tecnica.banking.repository.MovementsRepository;
import com.prueba.tecnica.banking.service.MovementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovementsServiceImpl implements MovementsService {
    private final MovementsRepository movementsRepository;
    private final AccountRepository accountRepository;

    @Override
    public Movements saveMovements(Movements movements, CommonHeaders commonHeaders) {
        Account accountAffect = accountRepository.findByAccountNumber(movements.getAccount().getAccountNumber())
                .orElseThrow(() -> new BankingException(HttpStatus.NOT_FOUND.toString(), "The associated account was not found"));
        if (movements.getMovementType().equals("CREDITO")) {
            movements.setBalance(accountAffect.getBalance() + movements.getAmount());
        } else if (movements.getMovementType().equals("DEBITO")) {
             if(accountAffect.getBalance() < movements.getAmount()) {
                 throw new BankingException(HttpStatus.NOT_ACCEPTABLE.toString(),
                         "Balance not available. You have "+ accountAffect.getBalance() + " USD and want to debit " + movements.getAmount() + " USD");
             } else {
                 movements.setBalance(accountAffect.getBalance() - movements.getAmount());
             }
        }
        accountAffect.setBalance(movements.getBalance());
        accountRepository.save(accountAffect);
        return movementsRepository.save(movements);
    }

    @Override
    public Movements updateMovements(Movements movements, CommonHeaders commonHeaders) {
        return null;
    }
}
