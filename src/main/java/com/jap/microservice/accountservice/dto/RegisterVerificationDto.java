package com.jap.microservice.accountservice.dto;

import lombok.Data;

@Data
public class RegisterVerificationDto {
    private String email;
    private String otp;
}
