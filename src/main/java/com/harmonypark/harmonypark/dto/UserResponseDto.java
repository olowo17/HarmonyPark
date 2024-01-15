package com.harmonypark.harmonypark.dto;


import com.harmonypark.harmonypark.entities.enums.Role;
import com.harmonypark.harmonypark.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
        private String id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String address;
        private String plateNumber;
        private String email;
        private LocalDateTime registrationDate;
        private Status status;
        private Role role;
        private boolean isTwoFactorEnabled;
        private String validOtp;

}
