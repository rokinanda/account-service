package com.jap.microservice.accountservice.feignclient;

import com.jap.microservice.accountservice.dto.RegisterCheckDto;
import com.jap.microservice.accountservice.dto.RegisterVerificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "otp")
public interface OTPClient {
    @PostMapping("/request")
    ResponseEntity<?> requestOTP(@RequestBody RegisterCheckDto registerCheckDto);

    @GetMapping("/test-loadBalancer")
    String testLoadBalancer();

    @PostMapping("/verification")
    ResponseEntity<?> verificationOTP(@RequestBody RegisterVerificationDto registerVerificationDto);
}
