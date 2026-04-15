package com.example.demo.model;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorID;

    private String authorName;
}
