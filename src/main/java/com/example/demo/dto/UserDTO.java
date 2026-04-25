package com.example.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String role;

    private String status;
    private String banReason;
    private String email;
    private String phoneNumber;
    private String address;
}