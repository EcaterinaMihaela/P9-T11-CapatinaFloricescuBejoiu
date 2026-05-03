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
    private String userName;

    @ManyToOne
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_ID")
    private Book book;
}