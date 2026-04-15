package com.example.demo.model;
import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.Data;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;

    private LocalDate reviewDate;
    private int rating;
    private String reviewText;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;
}