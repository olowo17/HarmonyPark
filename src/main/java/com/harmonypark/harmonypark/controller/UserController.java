package com.harmonypark.harmonypark.controller;

import com.harmonypark.harmonypark.dto.UserRequestDto;
import com.harmonypark.harmonypark.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImp userServiceImp;
    @GetMapping
    public ResponseEntity<?> getAllUsers (Pageable pageable){
        var users = userServiceImp.getAllUsers(pageable);
        return new ResponseEntity <> (users, HttpStatus.OK);
    }
    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@RequestParam String id){
        var user = userServiceImp.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping
    public String registerNewUser (@RequestBody UserRequestDto userRequestDto){
       return   userServiceImp.registerUser(userRequestDto);

    }
}
