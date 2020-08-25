package com.example.springdemo.service;

import com.example.springdemo.entity.Account;

import java.util.List;

public interface AccountService {
    public List<Account> findAllAccount() throws Exception;

    public Account createAccount(Account account) throws Exception;

    public Account updateAccount(Account account) throws Exception;

    public Account disableAccount(int accountId) throws Exception;

    public Account enableAccount(int accountId) throws Exception;

    public Account getAccountInformation(String token) throws Exception;

}
