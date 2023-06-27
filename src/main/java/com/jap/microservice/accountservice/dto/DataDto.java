package com.jap.microservice.accountservice.dto;


import lombok.Data;

@Data
public class DataDto {
    Long id;
    int amount;
    String email;
}
