package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.exceptions.InvalidCredentialsException;
import com.example.demo.exceptions.RoleNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new InvalidCredentialsException("Username is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new InvalidCredentialsException("Email is already registered");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.setRole(userRole);

        userRepository.save(user);
        return user;
    }

    public String verify(LoginRequestDTO user) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            } else {
                throw new InvalidCredentialsException("Invalid username or password");
            }

        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
