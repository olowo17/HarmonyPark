package com.harmonypark.harmonypark.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.harmonypark.harmonypark.entities.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2749436181809833674L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("name")
    private String name;


    @JsonProperty("role")
    private Role role;

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;


}