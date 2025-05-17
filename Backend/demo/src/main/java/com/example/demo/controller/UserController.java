package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.Users;
import com.example.demo.service.JWTService;
import com.example.demo.service.RatingService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Users user) {
        Users created = userService.register(user);
        String token = jwtService.generateToken(created.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Registration successful",
                "token", token
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token  = userService.verify(loginRequestDTO);
        System.out.println(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Success",
                "token", token
        ));

    }

}
