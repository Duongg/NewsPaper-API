package com.example.springdemo.serviceImp;


import com.example.springdemo.entity.Account;
import com.example.springdemo.entity.AccountRole;
import com.example.springdemo.entity.Role;
import com.example.springdemo.repositories.AccountRepository;
import com.example.springdemo.repositories.AccountRoleRepository;
import com.example.springdemo.repositories.RoleRepository;
import com.example.springdemo.security.JWTVerifier;
import com.example.springdemo.service.AccountService;
import com.example.springdemo.util.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImp implements AccountService {
    private static final Logger logger = Logger.getLogger(AccountServiceImp.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    @Override
    public List<Account> findAllAccount() throws Exception {
        logger.info("Begin service findAllAccount");
        try {
            List<Account> accountList = new ArrayList<>();
            accountList = accountRepository.findAll();
            return accountList;
        } finally {
            logger.info("End service findAllAccount");
        }
    }

    @Override
    public Account createAccount(Account account) throws Exception {
        logger.info("Begin service createAccount");
        try {
            if (accountRepository.findByUsername(account.getUsername()) != null) {
                throw new Exception(Message.ACCOUNT_EXIST);
            } else {
                Role role = roleRepository.getRoleByRoleName("user");
                account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
                account.setEmail(account.getEmail());
                account.setCreatedAt(new Timestamp(new Date().getTime()));
                account.setStatusActive(true);
                accountRepository.save(account);

                account = accountRepository.findByUsername(account.getUsername());
                AccountRole accountRole = accountRoleRepository.findByAccountIdAndRoleId(account.getId(), role.getId());

                if (accountRole == null) {
                    accountRole = new AccountRole();
                    accountRole.setAccountId(account.getId());
                    accountRole.setRoleId(role.getId());
                    accountRole.setCreatedAt(new Timestamp(new Date().getTime()));
                    accountRole.setStatusActive(true);
                    accountRoleRepository.save(accountRole);
                }
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            logger.info("End service createAccount");
        }
    }

    @Override
    public Account updateAccount(Account account) throws Exception {
        logger.info("Begin service updateAccount");
        try {
            if (accountRepository.findByUsername(account.getUsername()) == null) {
                throw new Exception("Account is not exist");
            } else {
                Account accountUpdate = accountRepository.findByUsername(account.getUsername());
                if(account.getId() == accountUpdate.getId()){
                    accountUpdate.setUsername(account.getUsername());
                    accountUpdate.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
                    accountUpdate.setEmail(account.getEmail());
                    accountUpdate.setModifiedAt(new Timestamp(new Date().getTime()));
                    accountUpdate.setStatusActive(account.isStatusActive());
                    accountRepository.save(account);
                }
                return account;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            logger.info("End service createAccount");
        }
    }

    private Account changeStatus(Account account){
        if(account.isStatusActive()){
            account.setStatusActive(false);
        }else {
            account.setStatusActive(true);
        }
        return account;
    }
    @Override
    public Account disableAccount(int accountId) throws Exception {
        logger.info("Begin service disableAccount");
        try{
            Account account = accountRepository.findById(accountId);
            if(account == null){
                throw new Exception("Account is not exist");
            }else {
                if(account.isStatusActive()){
                   account = changeStatus(account);
                }
                accountRepository.save(account);
                return account;
            }
        }finally {
            logger.info("End service disableAccount");
        }
    }

    @Override
    public Account enableAccount(int accountId) throws Exception {
        logger.info("Begin service enableAccount");
        try{
            Account account = accountRepository.findById(accountId);
            if(account == null){
                throw new Exception("Account is not exist");
            }else {
                if(!account.isStatusActive()){
                    account = changeStatus(account);
                }
                accountRepository.save(account);
                return account;
            }
        }finally {
            logger.info("End service enableAccount");
        }
    }

    @Override
    public Account getAccountInformation(String token) throws Exception {
        logger.info("Begin service getAccountInformation");
        try{
            Account account = accountRepository.findByUsername(JWTVerifier.USERNAME);
            if(account == null){
                throw new Exception("Account is not exist");
            }
            return account;
        }finally {
            logger.info("End service getAccountInformation");
        }
    }
}
