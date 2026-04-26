package com.example.demo.model;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookID;

    private String bookTitle;
    private String bookDescription;

    @Column(unique = true)
    private String ISBN;

    private int availableStock = 0;
    private String status = "AVAILABLE";

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisherID")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Publisher publisher;
}
