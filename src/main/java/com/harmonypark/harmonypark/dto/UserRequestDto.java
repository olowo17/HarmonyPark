package com.harmonypark.harmonypark.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harmonypark.harmonypark.entities.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class UserRequestDto {

    @Column(unique = true)
    @NotBlank(message = "firstName must not be blank")
    private String firstName;

    @Column(unique = true)
    @NotBlank(message = "lastName must not be blank")
    private  String lastName;

    @Column(unique = true)
    @Email(message = "please enter a valid email")
    @NotBlank(message = "email must not be blank")
    private String email;

    @NotBlank(message = "email must not be blank")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "phone number must not be blank")
    private String phoneNumber;

    @NotBlank(message = "address must not be blank")
    private String address;

    @Column(unique = true)
    @NotBlank(message = "plateNumber must not be blank")
    private String plateNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}
