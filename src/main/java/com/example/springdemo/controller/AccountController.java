package com.example.springdemo.controller;

import com.example.springdemo.entity.Account;
import com.example.springdemo.repositories.AccountRepository;
import com.example.springdemo.service.AccountService;
import com.example.springdemo.util.URL;
import dto.ServiceResponseDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URL.API)
public class AccountController {

    private static final Logger logger = Logger.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping(URL.GET_ACCOUNT)
    public ResponseEntity findAllAccount() {
        logger.info("Begin service findAllAccount");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            List<Account> accountList = accountService.findAllAccount();
            response.setData(accountList);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("End service findAllAccount");
        }
    }

    @PostMapping(URL.CREATE_ACCOUNT)
    public ResponseEntity createAccount(@RequestBody Account account) {
        logger.info("Begin service CreateAccount");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            accountService.createAccount(account);
            response.setMessage("Created");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("End service CreateAccount");
        }
    }

    @PutMapping(URL.UPDATE_ACCOUNT)
    public ResponseEntity updateAccount(@RequestBody Account account) {
        logger.info("Begin service updateAccount");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            accountService.updateAccount(account);
            response.setMessage("Updated");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.equals(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("End service updateAccount");
        }
    }

    @PutMapping(URL.DISABLE_ACCOUNT + "/{id}")
    public ResponseEntity disableAccount(@PathVariable int id) {
        logger.info("Begin controller disableAccount");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            accountService.disableAccount(id);
            response.setMessage("Disabled");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("Begin controller disableAccount");
        }
    }

    @PutMapping(URL.ENABLE_ACCOUNT + "/{id}")
    public ResponseEntity enableAccount(@PathVariable int id) {
        logger.info("Begin controller enableAccount");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            accountService.enableAccount(id);
            response.setMessage("Enabled");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("Begin controller enableAccount");
        }
    }

    @GetMapping(URL.ACCOUNT_INFORMATION)
    public ResponseEntity getInfomation(@RequestHeader String authorization) {
        logger.info("Begin controller " + "getInfomation");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            Account account = accountService.getAccountInformation(authorization);
            response.setData(account);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("Begin controller getInfomation");
        }
    }
}
