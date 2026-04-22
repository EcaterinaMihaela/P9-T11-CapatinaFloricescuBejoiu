package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;


@Data
public class SecurityLogDTO {

    private Long logID;
    private String actionType;
    private LocalDate logDate;
    private LocalTime logTime;
    private String affectedTable;
    private Long userID;

    // getters & setters
}