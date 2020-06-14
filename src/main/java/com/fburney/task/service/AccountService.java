package com.fburney.task.service;

import com.fburney.task.exception.AccountNotFoundException;
import com.fburney.task.model.Account;
import com.fburney.task.model.AccountParameter;
import com.fburney.task.model.AccountResponse;
import com.fburney.task.model.AccountBalance;
import com.fburney.task.reactive.ReactiveAccountFetcher;
import com.fburney.task.repo.AccountRepo;
import com.fburney.task.service.currencyconversion.CurrencyConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    ReactiveAccountFetcher reactiveAccountFetcher;

    @Autowired
    CurrencyConverter currencyConverter;

    private final Scheduler reactiveAccountScheduler = Schedulers.
            newParallel("reactive-Account", 10, true);


    public void saveAccount(AccountParameter account) {
        Account dbAccount = Account.builder().id(account.getAccountId()).
                firstName(account.getFirstName()).lastName(account.getLastName())
                .currencyType(account.getCurrencyCode())
                .currentBalance(0.0)
                .joinDate(new Date()).build();

        accountRepo.save(dbAccount);
    }

    public void updateAccount(AccountParameter account) {
        Account savedAccount = reactiveAccountFetcher.fetchAccount(account.getAccountId());
        if (savedAccount == null)
            throw new AccountNotFoundException();

        savedAccount.setCurrencyType(account.getCurrencyCode());
        savedAccount.setFirstName(account.getFirstName());
        savedAccount.setLastName(account.getLastName());

        accountRepo.save(savedAccount);
    }

    public void updateAccountBalance(AccountBalance accountBalance) {
        Account savedAccount = reactiveAccountFetcher.fetchAccount(accountBalance.getAccountId());
        if (savedAccount == null)
            throw new AccountNotFoundException();

        Double newBalance = currencyConverter.convert(accountBalance.getBalance(),
                accountBalance.getCurrencyCode(),
                savedAccount.getCurrencyType());
        savedAccount.setCurrentBalance(newBalance);

        accountRepo.save(savedAccount);
    }


    public Flux<AccountResponse> getAccounts() {

        List<Account> accounts = null;
        final Flux<AccountResponse> callableMono = Flux.defer(() -> Flux.just(
                reactiveAccountFetcher.fetchAccounts())
                .subscribeOn(reactiveAccountScheduler));

        return callableMono;
    }

    public Flux<AccountBalance> getBalanceOfAccount(String id, String currencyType) {

        final Flux<AccountBalance> callableMono = Flux.defer(() -> Flux.just(
                reactiveAccountFetcher.fetchAccountBalance(id,currencyType))
                .subscribeOn(reactiveAccountScheduler));

        return callableMono;
    }
}
