package com.harmonypark.harmonypark.entities;
import com.harmonypark.harmonypark.entities.enums.Role;
import com.harmonypark.harmonypark.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
    private Role role;

    public User(){
        this.registrationDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

}
