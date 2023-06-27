package com.jap.microservice.accountservice.controller;

import com.jap.microservice.accountservice.db.entity.Account;
import com.jap.microservice.accountservice.db.repository.AccountRepository;
import com.jap.microservice.accountservice.db.repository.MahasiswaRepository;
import com.jap.microservice.accountservice.dto.*;
import com.jap.microservice.accountservice.service.AccountService;
import com.jap.microservice.accountservice.service.LoginService;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@CrossOrigin("*")
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final LoginService loginService;

    private  final MahasiswaRepository mahasiswaRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository, LoginService loginService, MahasiswaRepository mahasiswaRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.loginService = loginService;
        this.mahasiswaRepository = mahasiswaRepository;
    }


    @PostMapping("/check")
    public ResponseEntity<?> registerCheck(@RequestBody RegisterCheckDto registerCheckDto) {
        log.debug("register {}", registerCheckDto);
        return accountService.registerCheck(registerCheckDto);
    }

    @GetMapping("/load")
    public String testLoadBalancer() {
        return accountService.testLoadBalancer();
    }

    @PostMapping("/verification")
    public ResponseEntity<?> verification(@RequestBody RegisterVerificationDto registerVerificationDto) {
        return accountService.verification(registerVerificationDto);
    }

    @PostMapping("/password")
    public ResponseEntity<?> registerPassword(@RequestBody RegisterPasswordDto registerPasswordDto) {
        return accountService.registerPassword(registerPasswordDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return loginService.login(loginDto);
    }

    @GetMapping("/updatemahasiswa/{id}/{nama}")
    public ResponseEntity<?> tes(@PathVariable String id, @PathVariable String nama) throws InterruptedException {
        var datamhs = mahasiswaRepository.findById(id).get();
        datamhs.setNama(nama);
        mahasiswaRepository.save(datamhs);
//        updateEmail(dataDto.getEmail(), dataDto.getId());
//        accountRepository.updateAmount(dataa.get().getAmount() - dataDto.getAmount(), dataDto.getId());
//        var data2 = accountRepository.findById(dataDto.getId());
//        System.out.println(data2.get().getAmount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/updateamount/{id}/{amount}")
    public ResponseEntity<?> tes2(@PathVariable Long id, @PathVariable int amount) throws InterruptedException {
        var threatlog = Thread.currentThread().getName();
        System.out.println("dari update amount "+ threatlog);
//        System.out.println("nama yang ngehit "+ namahit);
       updateAmount(amount, id, threatlog);

//        accountRepository.updateAmount(dataa.get().getAmount() - dataDto.getAmount(), dataDto.getId());
//        var data2 = accountRepository.findById(dataDto.getId());
//        System.out.println(data2.get().getAmount());
        return ResponseEntity.ok().build();
    }

    private void updateMahasiswa(String email, Long id) {
        var dataa = accountRepository.findById(id).get();
        dataa.setEmail(email);
        accountRepository.save(dataa);
    }

    private void updateAmount(int amount, Long id, String thread) throws InterruptedException {
        save(amount, id);
        System.out.println("Selesai");
        System.out.println("Thread ke " + thread);
    }

    private void save(int amount, Long id) {
        var dataa = accountRepository.findWithLockingById(id).get();
        int hasil = dataa.getAmount() - amount;
        System.out.println("hasil pengurangan "+ hasil);
        dataa.setAmount(hasil);
        accountRepository.saveAndFlush(dataa);
    }

//    private void save(int amount, Long id) {
//        var dataa = accountRepository.findById(id).get();
//        int hasil = dataa.getAmount() - amount;
//        System.out.println("hasil pengurangan "+ hasil);
//        dataa.setAmount(hasil);
//        accountRepository.saveAndFlush(dataa);
//    }


}
