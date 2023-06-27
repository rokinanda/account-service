package com.jap.microservice.accountservice.dto;

import lombok.Data;

@Data
public class RegisterPasswordDto {
    private String email;
    private String password;
}
