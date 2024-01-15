package com.harmonypark.harmonypark.controller;

import com.harmonypark.harmonypark.dto.AuthenticationResponse;
import com.harmonypark.harmonypark.dto.LoginRequest;
import com.harmonypark.harmonypark.service.LoginService;
import com.harmonypark.harmonypark.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class LoginController {
    private final LoginService loginService;
    private final UserServiceImp userServiceImp;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUserEntity(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.loginUser(loginRequest), HttpStatus.OK);
    }
    @GetMapping("/login/verify-login")
    public ResponseEntity<AuthenticationResponse> validateOtp(@RequestParam String email, @RequestParam String otp){
        return userServiceImp.validateOtp(email, otp);
    }
}