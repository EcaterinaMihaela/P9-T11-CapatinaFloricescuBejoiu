package com.example.demo.dto;

import lombok.Data;

@Data
public class UserProfileResponseDTO {

    private String username;
    private String role;

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;
    private String address;
}