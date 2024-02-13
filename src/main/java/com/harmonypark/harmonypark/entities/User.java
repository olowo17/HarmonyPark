package com.harmonypark.harmonypark.entities;
import com.harmonypark.harmonypark.entities.enums.Role;
import com.harmonypark.harmonypark.entities.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Document
public class User implements UserDetails {
    @Id
    private String id;
    @NotNull(message = "Missing required field firstname")
    @Indexed(unique = true)
    private String firstName;

    @NotNull(message = "Missing required field lastname")
    @Indexed(unique = true)
    private String lastName;

    @NotNull(message = "Missing required field phone number")
    @Indexed(unique = true)
    private String phoneNumber;

    @NotNull(message = "Missing required field address")
    @Indexed(unique = true)
    private String address;

    @NotNull(message = "Missing required field plate number")
    @Indexed(unique = true)
    private String plateNumber;

    @NotNull(message = "Missing required field email")
    @Indexed(unique = true)
    private String email;

    private String password;
    private LocalDateTime registrationDate;
    private Status status;
    private Role role;
    private boolean isTwoFactorEnabled;
    private String validOtp;

    public User(){
        this.registrationDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.isTwoFactorEnabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
