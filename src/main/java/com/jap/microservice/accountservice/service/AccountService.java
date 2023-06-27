package com.jap.microservice.accountservice.service;

import com.jap.microservice.accountservice.db.entity.Account;
import com.jap.microservice.accountservice.db.entity.TempAccount;
import com.jap.microservice.accountservice.db.repository.AccountRepository;
import com.jap.microservice.accountservice.db.repository.TempAccountRepository;
import com.jap.microservice.accountservice.dto.RegisterCheckDto;
import com.jap.microservice.accountservice.dto.RegisterPasswordDto;
import com.jap.microservice.accountservice.dto.RegisterVerificationDto;
import com.jap.microservice.accountservice.feignclient.OTPClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TempAccountRepository tempAccountRepository;
    private final OTPClient otpClient;

    public AccountService(AccountRepository accountRepository, TempAccountRepository tempAccountRepository, OTPClient otpClient) {
        this.accountRepository = accountRepository;
        this.tempAccountRepository = tempAccountRepository;
        this.otpClient = otpClient;
    }

    public ResponseEntity<?> registerCheck(RegisterCheckDto registerCheckDto) {
        // check data di postgres
        var accountByEmail = accountRepository.getFirstByEmail(registerCheckDto.getEmail());
        if (accountByEmail != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User sudah terdaftar");
        }
        // check data di redis
        var tempAccountByEmail = tempAccountRepository.getFirstByEmail(registerCheckDto.getEmail());
        if (tempAccountByEmail != null) {
            return ResponseEntity.ok().build();
        }
        //  save ke temp redis
        tempAccountByEmail = new TempAccount();
        tempAccountByEmail.setEmail(registerCheckDto.getEmail());
        tempAccountByEmail.setValid(false);
        tempAccountRepository.save(tempAccountByEmail);

        // request otp
        try {
            otpClient.requestOTP(registerCheckDto);
        } catch (FeignException.FeignClientException ex) {
            return ResponseEntity.status(ex.status()).body(ex.contentUTF8());
        }
        return ResponseEntity.ok().build();

    }

    public String testLoadBalancer() {
        return otpClient.testLoadBalancer();
    }

    public ResponseEntity<?> verification(RegisterVerificationDto registerVerificationDto) {
        // check redis
        TempAccount tempAccount = tempAccountRepository.getFirstByEmail(registerVerificationDto.getEmail());
        if (tempAccount.getEmail() == null) return ResponseEntity.notFound().build();
        // verification otp
        try {
            otpClient.verificationOTP(registerVerificationDto);
        } catch (FeignException.FeignClientException ex) {
            ex.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }

        // update verification
        tempAccount.setValid(true);
        tempAccountRepository.save(tempAccount);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> registerPassword(RegisterPasswordDto registerPasswordDto) {
        // check redis
        TempAccount tempAccount = tempAccountRepository.getFirstByEmail(registerPasswordDto.getEmail());
        if (tempAccount.getEmail() == null) return ResponseEntity.notFound().build();
        // verification valid
        if(!tempAccount.isValid()) return ResponseEntity.unprocessableEntity().build();
        // save to postgres
        Account account = new Account();
        account.setEmail(registerPasswordDto.getEmail());
        account.setPassword(registerPasswordDto.getPassword());
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
        // delete temp account
        tempAccountRepository.delete(tempAccount);
        return ResponseEntity.ok().build();
    }
}
