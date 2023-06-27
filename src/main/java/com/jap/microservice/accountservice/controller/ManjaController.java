package com.jap.microservice.accountservice.controller;

import com.jap.microservice.accountservice.db.repository.AccountRepository;
import com.jap.microservice.accountservice.dto.DataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anakmuda")
public class ManjaController {
    private final AccountRepository accountRepository;

    public ManjaController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/updateamount2")
    public ResponseEntity<?> tes2(@RequestBody DataDto dataDto) {
        var threatlog = Thread.currentThread().getName();
        System.out.println("dari update amount "+ threatlog);
        updateAmount(dataDto.getAmount(), dataDto.getId());

//        accountRepository.updateAmount(dataa.get().getAmount() - dataDto.getAmount(), dataDto.getId());
//        var data2 = accountRepository.findById(dataDto.getId());
//        System.out.println(data2.get().getAmount());
        return ResponseEntity.ok().build();
    }

    private void updateAmount(int amount, Long id) {
        var dataa = accountRepository.findById(id).get();
        int hasil = dataa.getAmount() - amount;
        dataa.setAmount(hasil);
        accountRepository.save(dataa);
    }
}
