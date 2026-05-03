package com.example.demo.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    private LocalDate sendingDate;
    private LocalTime sendingTime;

    private String type;
    private String message;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;


    private boolean isRead = false;
}
