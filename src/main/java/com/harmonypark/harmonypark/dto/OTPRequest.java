package com.harmonypark.harmonypark.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OTPRequest {
    private String otp;
    private String email;
}
