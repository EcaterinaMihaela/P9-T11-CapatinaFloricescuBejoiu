package com.example.demo.model;
import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorID;

    @Column(nullable = false)
    private String authorName;
}
