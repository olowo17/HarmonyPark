package com.harmonypark.harmonypark.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harmonypark.harmonypark.entities.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String plateNumber;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private Status status;

    public User(){
        this.registrationDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    public User(String id, String email, String password, String phoneNumber,
                String address, String plateNumber, LocalDateTime registrationDate, Status status) {
    }
}
