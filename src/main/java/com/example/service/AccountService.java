package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;



@Service
public class AccountService {

    private AccountRepository arepo;

    @Autowired
    public AccountService(AccountRepository arepo){
        this.arepo = arepo;
    }

    //======================================== Create Account : not tested ================================================

    public Optional<Account> createAccount(Account a) {

        Optional<Account> existingAccount = arepo.findByUsername(a.getUsername());
        
        //Username already exists
        if (existingAccount.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(arepo.save(a));

    }


    //======================================== Login to Account : not tested ================================================

    public Optional<Account> loginToAccount(Account a) {
        
        return arepo.findByUsername(a.getUsername()).filter(account -> account.getPassword().equals(a.getPassword()));

    }

}

