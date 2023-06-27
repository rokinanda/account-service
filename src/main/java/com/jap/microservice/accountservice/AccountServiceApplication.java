package com.jap.microservice.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
		var cek = Thread.currentThread();
		System.out.println("ini apa "+ cek);
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
