package com.fburney.task.reactive;


import com.fburney.task.exception.AccountNotFoundException;
import com.fburney.task.model.Account;
import com.fburney.task.model.AccountBalance;
import com.fburney.task.model.AccountParameter;
import com.fburney.task.model.AccountResponse;
import com.fburney.task.repo.AccountRepo;
import com.fburney.task.service.currencyconversion.CurrencyConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReactiveAccountFetcher {
    @Autowired
    AccountRepo accountRepo;

    @Autowired
    CurrencyConverter currencyConverter;


    //would have preferred reactive mongo db repo and
    // made this call also reactive but
    // for simplicity purpose using h2 db
    public AccountResponse fetchAccounts() {

        List<Account> accounts = accountRepo.findAll();

        Set<AccountParameter> accountParameters = accounts.stream()
                .map(account -> AccountParameter.builder()
                        .accountId(account.getId())
                        .currencyCode(account.getCurrencyType())
                        .firstName(account.getFirstName())
                        .lastName(account.getLastName()).build())
                .collect(Collectors.toSet());

        return AccountResponse.builder().accounts(accountParameters).build();
    }

    public AccountBalance fetchAccountBalance(String id , String CurrencyType) {

        Account account = this.fetchAccount(id);
        Double balance = currencyConverter.convert(account.getCurrentBalance(),
                account.getCurrencyType(),CurrencyType);
        return AccountBalance.builder()
                .accountId(account.getId())
                .balance(balance)
                .currencyCode(account.getCurrencyType())
                .build();
    }

    public Account fetchAccount(String id) {
        try {
            return accountRepo.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AccountNotFoundException();
        }
    }

}
