package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String username;
    private String password;

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;
    private String address;
}