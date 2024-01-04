package com.harmonypark.harmonypark.entities;

import com.harmonypark.harmonypark.entities.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Admin extends User {

    public Admin(String id,
                 @Email(message = "please enter a valid email")
                 @NotBlank(message = "email must not be blank") String email,
                 @NotBlank(message = "email must not be blank") String password,
                 @NotBlank(message = "phone number must not be blank") String phoneNumber,
                 @NotBlank(message = "address must not be blank") String address,
                 @NotBlank(message = "plateNumber must not be blank") String plateNumber,
                 LocalDateTime registrationDate, Status status) {
        super(id, email, password, phoneNumber, address, plateNumber, registrationDate, status);
    }
}
