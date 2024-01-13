package com.harmonypark.harmonypark.service;

import com.harmonypark.harmonypark.dto.UserRequestDto;
import com.harmonypark.harmonypark.dto.UserResponseDto;
import com.harmonypark.harmonypark.entities.User;
import com.harmonypark.harmonypark.exception.UserNotFoundException;
import com.harmonypark.harmonypark.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
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
    public String registerUser(UserRequestDto userRequestDto) {
        var user = new User();
        user.setAddress(userRequestDto.getAddress());
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setPlateNumber(userRequestDto.getPlateNumber());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(userRequestDto.getRole());
        userRepository.save(user);
        return "success";
    }
}
