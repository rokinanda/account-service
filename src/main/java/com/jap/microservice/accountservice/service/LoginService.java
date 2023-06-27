package com.jap.microservice.accountservice.service;

import com.jap.microservice.accountservice.db.repository.AccountRepository;
import com.jap.microservice.accountservice.dto.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AccountRepository accountRepository;

    public LoginService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> login(LoginDto loginDto) {
        var checkEmail = accountRepository.getFirstByEmail(loginDto.getEmail());
        if(checkEmail == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Belum terdaftar");
        }
        var checkPassword = accountRepository.getFirstByPassword(loginDto.getPassword());
        if(checkPassword == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Password salah");
        }

        return ResponseEntity.ok().build();
    }
}
