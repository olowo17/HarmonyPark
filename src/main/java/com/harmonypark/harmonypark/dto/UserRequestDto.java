package com.harmonypark.harmonypark.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harmonypark.harmonypark.entities.enums.Role;
import com.harmonypark.harmonypark.entities.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "firstName must not be blank")
    private String firstName;

    @NotBlank(message = "lastName must not be blank")
    private  String lastName;


    @Email(message = "please enter a valid email")
    @NotBlank(message = "email must not be blank")
    private String email;

    @NotBlank(message = "email must not be blank")
    private String password;

    @NotBlank(message = "phone number must not be blank")
    private String phoneNumber;

    @NotBlank(message = "address must not be blank")
    private String address;

    @NotBlank(message = "plateNumber must not be blank")
    @NotNull(message = "Missing required field email")
    @Indexed(unique = true)
    private String plateNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;
}
