package com.harmonypark.harmonypark.dto;

import com.harmonypark.harmonypark.entities.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;




public record GenerateQrRequest(
         String firstName,
         String lastName,
         String phoneNumber,
         String address,
         String plateNumber,
         String email,
         LocalDateTime registrationDate,
         Status status) {
}
