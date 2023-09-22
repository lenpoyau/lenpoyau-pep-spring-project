package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

public class AccountService {
    AccountRepository accountRepository;

    /*
     * The AccountRepository has been Autowired into this class via Constructor injection below. A bean for it 
     * will be injected and made available that way.
     * @param accountRepository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /*
     * Add a new user account in the database and return it
     */
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

     /*
     * retrieve and return all accounts found in the db
     */
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
}
