package com.example.demo.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
@Entity
public class SecurityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logID;

    private String actionType;
    private LocalDate logDate;
    private LocalTime logTime;

    private String affectedTable;

    @ManyToOne
    private User user;
}
