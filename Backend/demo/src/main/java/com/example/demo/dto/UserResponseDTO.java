package com.example.demo.dto;

import com.example.demo.model.Users;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String username;
    private String role;

    public UserResponseDTO(Users user) {
        this.username = user.getUsername();
        this.role = user.getRole().getName();
    }

}
