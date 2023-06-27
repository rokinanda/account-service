package com.jap.microservice.accountservice.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
