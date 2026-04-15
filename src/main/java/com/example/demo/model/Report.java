package com.example.demo.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportID;

    private String type;
    private LocalDate generationDate;
    private LocalTime generationTime;

    private String format;

    @ManyToOne
    private User user;
}
