package com.harmonypark.harmonypark.service;

import com.harmonypark.harmonypark.dto.OTPRequest;
import com.harmonypark.harmonypark.dto.UserRequestDto;
import com.harmonypark.harmonypark.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
   public Page<UserResponseDto> getAllUsers(Pageable pageable);
   public UserResponseDto getUserById (String id);
   public UserResponseDto registerUser (UserRequestDto userRequestDto);
   public void enableTwoFactor(String email);
//   public String  validateOtp(OTPRequest otpRequest);
}
