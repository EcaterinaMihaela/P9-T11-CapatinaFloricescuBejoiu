package com.example.demo.model;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherID;

    private String publisherName;
}
