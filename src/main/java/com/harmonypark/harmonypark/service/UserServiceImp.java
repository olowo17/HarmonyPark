package com.harmonypark.harmonypark.service;

import com.harmonypark.harmonypark.dto.AuthenticationResponse;
import com.harmonypark.harmonypark.dto.OTPRequest;
import com.harmonypark.harmonypark.dto.UserRequestDto;
import com.harmonypark.harmonypark.dto.UserResponseDto;
import com.harmonypark.harmonypark.entities.User;
import com.harmonypark.harmonypark.exception.UserNotFoundException;
import com.harmonypark.harmonypark.repositories.UserRepository;
import com.harmonypark.harmonypark.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper = new ModelMapper();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(user -> modelMapper.map(user, UserResponseDto.class));
    }

    @Override
    public UserResponseDto getUserById(String id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {

        var user = new User();
        user.setAddress(userRequestDto.getAddress());
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setPlateNumber(userRequestDto.getPlateNumber());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRole(userRequestDto.getRole());
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public void enableTwoFactor(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setTwoFactorEnabled(true);
        userRepository.save(user);
    }

    public static Long generateOTP() {
        Random rnd = new Random();
        return (long) rnd.nextInt(999999);
    }

    public ResponseEntity<AuthenticationResponse> validateOtp(String email, String userProvidedOtp) {
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        var storedEncodedOtp = user.getValidOtp();
        if (passwordEncoder.matches(userProvidedOtp, storedEncodedOtp)) {
            // OTP is valid, generate a token and construct the AuthenticationResponse
            var userToken = jwtService.generateJwtToken(user);
            var name = user.getFirstName() + " " + user.getLastName();

            // Create and return a ResponseEntity with the AuthenticationResponse
            var authenticationResponse = new AuthenticationResponse(userToken, name, user.getRole());
            return ResponseEntity.ok(authenticationResponse);
        }

        // OTP is invalid, return a response indicating the failure
        return ResponseEntity.badRequest().body(null);
    }


}
